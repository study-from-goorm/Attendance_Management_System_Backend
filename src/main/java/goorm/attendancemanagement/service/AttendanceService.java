package goorm.attendancemanagement.service;

import goorm.attendancemanagement.domain.dao.Attendance;
import goorm.attendancemanagement.domain.dao.AttendanceStatus;
import goorm.attendancemanagement.domain.dao.Player;
import goorm.attendancemanagement.domain.dao.Session;
import goorm.attendancemanagement.domain.dto.AttendanceDetailsDto;
import goorm.attendancemanagement.domain.dto.AttendanceSessionDto;
import goorm.attendancemanagement.domain.dto.AttendanceSummaryDto;
import goorm.attendancemanagement.domain.dto.PlayerAttendanceDto;
import goorm.attendancemanagement.repository.AttendanceRepository;
import goorm.attendancemanagement.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
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
}
