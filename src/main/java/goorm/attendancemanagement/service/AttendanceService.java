package goorm.attendancemanagement.service;

import goorm.attendancemanagement.domain.dao.Attendance;
import goorm.attendancemanagement.domain.dao.AttendanceStatus;
import goorm.attendancemanagement.domain.dao.Player;
import goorm.attendancemanagement.domain.dao.Session;
import goorm.attendancemanagement.domain.dto.*;
import goorm.attendancemanagement.repository.AttendanceRepository;
import goorm.attendancemanagement.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final PlayerRepository playerRepository;

    @Scheduled(cron = "0 0 0 * * MON")
    public void createWeeklyAttendanceRecords() {
        List<Player> players = playerRepository.findAll();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < 5; i++) {
            LocalDate date = today.plusDays(i);

            for (Player player : players) {
                if (!attendanceRepository.existsByPlayerAndAttendanceDate(player, date)) {
                    attendanceRepository.save(new Attendance(player, date, AttendanceStatus.notEntered, new Session()));
                }
            }
        }
    }

    public AttendanceSummaryDto getAttendanceSummary(int playerId, int year, int month) {
        List<PlayerAttendanceDto> attendanceList = attendanceRepository.findAttendanceInfoByPlayerIdAndMonth(playerId, year, month);

        String playerName = attendanceList.stream()
                .findFirst()
                .map(PlayerAttendanceDto::getPlayerName)
                .orElseThrow(() -> new EntityNotFoundException("Can not found this player"));

        LocalDate startOfMonth = YearMonth.of(year, month).atDay(1);
        LocalDate endOfMonth = YearMonth.from(startOfMonth).atEndOfMonth();

        int totalDays = (int) IntStream.rangeClosed(1, endOfMonth.getDayOfMonth())
                .mapToObj(startOfMonth::withDayOfMonth)
                .filter(date -> !isWeekend(date))
                .count();

        Map<AttendanceStatus, Long> statusCount = attendanceList.stream()
                .collect(Collectors.groupingBy(PlayerAttendanceDto::getAttendanceStatus, Collectors.counting()));

        List<AttendanceDetailsDto> attendanceDetails = attendanceList.stream()
                .map(dto -> new AttendanceDetailsDto(dto.getAttendanceDate(), dto.getAttendanceStatus()))
                .collect(Collectors.toList());

        Map<AttendanceStatus, Long> initialStatusCount = EnumSet.allOf(AttendanceStatus.class).stream()
                .collect(Collectors.toMap(Function.identity(), status -> 0L));

        initialStatusCount.putAll(statusCount);

        long totalPresentCount = initialStatusCount.values().stream().mapToLong(Long::longValue).sum();
        long notEnteredCount = totalDays - totalPresentCount;
        initialStatusCount.put(AttendanceStatus.notEntered, notEnteredCount);

        return new AttendanceSummaryDto(playerName, year, month, totalDays, initialStatusCount, attendanceDetails);
    }

    public AttendanceSessionDto getSessionSummary(int playerId, int year, int month, int day) {
        Optional<Attendance> sessionList = attendanceRepository.findSessionInfoByPlayerIdAndDay(playerId, year, month, day);
        List<Session> sessionDetails = sessionList.stream()
                .map(Attendance::getSession).toList();

        return new AttendanceSessionDto(year, month, day, sessionDetails);
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    public GetCourseSessionByDateDto getCourseSessionByDate(int courseId, LocalDate date) {
        List<Attendance> session = attendanceRepository.findPlayerSessionFromCourseInfo(courseId, date);
        String courseName = session.stream()
                .findFirst()
                .map(Attendance -> Attendance.getPlayer().getCourse().getCourseName())
                .orElseThrow(() -> new EntityNotFoundException("Can not found this player"));

        List<PlayerSessionsDto> playerSessions = session.stream()
                .map(o -> new PlayerSessionsDto(o.getPlayer().getPlayerName(), o.getSession()))
                .collect(Collectors.toList());


        return new GetCourseSessionByDateDto(courseName, date, playerSessions);
    }

    @Transactional
    public GetCourseSessionByDateDto updateCourseSessionByDate(int courseId, LocalDate date, GetCourseSessionByDateDto sessions) {
        List<PlayerSessionsDto> playerSessionsDtos = new ArrayList<>();
        List<Player> byCourseCourseIdAndPlayerName = playerRepository.findByCourse_CourseIdAndPlayerName(courseId);
        for (Player player : byCourseCourseIdAndPlayerName) {
            List<Session> sessionList = sessions.getPlayerSessions().stream().filter(playerSessionsDto -> playerSessionsDto.getPlayerName().equals(player.getPlayerName()))
                    .map(PlayerSessionsDto::getSessionList).toList();
            int[] sessionArray = sessionList.stream()
                    .flatMapToInt(session -> IntStream.of(
                            session.getSessionOne(),
                            session.getSessionTwo(),
                            session.getSessionThree(),
                            session.getSessionFour(),
                            session.getSessionFive(),
                            session.getSessionSix(),
                            session.getSessionSeven(),
                            session.getSessionEight()
                    )).toArray();
            int statusCount = calculateSessionCount(sessionArray);

            Optional<Attendance> allByPlayerAndAttendanceDate = attendanceRepository.findAllByPlayerAndAttendanceDate(player, date);
            Attendance attendance;
            if (allByPlayerAndAttendanceDate.isPresent()) {
                attendance = allByPlayerAndAttendanceDate.get();
                attendance.updateSessionAndStatus(determindSession(sessionList), determineAttendanceStatus(statusCount));
            } else {
                attendance = new Attendance(
                        player,
                        date,
                        determineAttendanceStatus(statusCount),
                        determindSession(sessionList));
                attendance = attendanceRepository.save(attendance);
            }
            PlayerSessionsDto playerSessionsDto = new PlayerSessionsDto(player.getPlayerName(), attendance.getSession());
            playerSessionsDtos.add(playerSessionsDto);
        }


        return new GetCourseSessionByDateDto(
                sessions.getCourseName(),
                date,
                playerSessionsDtos
        );
    }

    public int calculateSessionCount(int[] sessionArray) {
        int sessionCount = 0;
        for (int session : sessionArray) {
            switch (session) {
                case 1:
                    sessionCount += 1;
                    break;
                case 2:
                case 3:
                case 4:
                    sessionCount += 2;
                    break;
                case 5:
                    sessionCount += 5;
                    break;
                case 6:
                    sessionCount += 6;
                    break;
                default:
                    sessionCount += 0;
                    break;
            }
        }
        return sessionCount;
    }

    public AttendanceStatus determineAttendanceStatus(int statusCount) {
        if (statusCount >= 1 && statusCount <= 8) {
            return AttendanceStatus.present;
        } else if (statusCount >= 9 && statusCount <= 12) {
            return AttendanceStatus.partiallyPresent;
        } else if ((statusCount >= 13 && statusCount <= 16) || statusCount == 40) {
            return AttendanceStatus.absent;
        } else if (statusCount == 48) {
            return AttendanceStatus.officiallyExcused;
        } else if (statusCount == 0){
            return AttendanceStatus.notEntered;
        } else {
            return null;
        }
    }

    public Session determindSession(List<Session> sessionList) {
        int sessionOne = sessionList.stream().mapToInt(Session::getSessionOne).sum();
        int sessionTwo = sessionList.stream().mapToInt(Session::getSessionTwo).sum();
        int sessionThree = sessionList.stream().mapToInt(Session::getSessionThree).sum();
        int sessionFour = sessionList.stream().mapToInt(Session::getSessionFour).sum();
        int sessionFive = sessionList.stream().mapToInt(Session::getSessionFive).sum();
        int sessionSix = sessionList.stream().mapToInt(Session::getSessionSix).sum();
        int sessionSeven = sessionList.stream().mapToInt(Session::getSessionSeven).sum();
        int sessionEight = sessionList.stream().mapToInt(Session::getSessionEight).sum();

        return new Session(sessionOne, sessionTwo, sessionThree, sessionFour, sessionFive, sessionSix, sessionSeven, sessionEight);
    }
}

