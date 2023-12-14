package goorm.attendancemanagement.service;

import goorm.attendancemanagement.domain.dao.*;
import goorm.attendancemanagement.domain.dto.*;
import goorm.attendancemanagement.repository.AttendanceRepository;
import goorm.attendancemanagement.repository.CourseRepository;
import goorm.attendancemanagement.repository.HolidayRepository;
import goorm.attendancemanagement.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PlayerService {

    private final CourseRepository courseRepository;
    private final PlayerRepository playerRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceService attendanceService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HolidayRepository holidayRepository;

    public List<GetPlayersByCourseDto> getPlayersByCourse(Integer courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        return course.getPlayers().stream()
                .map(player -> new GetPlayersByCourseDto(
                        course.getCourseName(),
                        player.getPlayerId(),
                        player.getPlayerName(),
                        player.getPlayerEmail()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createPlayer(CreatePlayerDto player) {
        boolean exists = playerRepository.existsByPlayerEmail(player.getPlayerEmail());

        if (exists) {
            throw new IllegalArgumentException("플레이어의 Email '" + player.getPlayerEmail() + "'가 이미 존재합니다.");
        }

        Course findCourse = courseRepository.findById(player.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + player.getCourseId()));

        Player newPlayer = new Player(player.getPlayerEmail(), passwordEncoder.encode(player.getPlayerPassword()), player.getPlayerName(), findCourse, Role.ROLE_PLAYER);
        playerRepository.save(newPlayer);

        LocalDate today = newPlayer.getCourse().getStartDate();

        for (int i = 0; i < 5; i++) {
            LocalDate date = today.plusDays(i);
            if (isWeekend(date)) {
                break;
            }

            if (isHoliday(date)) {
                continue;
            }

            attendanceRepository.save(new Attendance(newPlayer, date, AttendanceStatus.notEntered, new Session()));
        }
    }

    private boolean isHoliday(LocalDate date) {
        return holidayRepository.existsById(date);
    }


    public PlayerInfoDto getPlayerInfo(int playerId) {
        return playerRepository.findById(playerId)
                .map(player -> new PlayerInfoDto(
                        player.getCourse().getCourseName(),
                        player.getPlayerEmail(),
                        player.getPlayerName()))
                .orElseThrow(() -> new EntityNotFoundException("Can not found this player"));  // 플레이어가 없을 경우 null 반환
    }
    public void changePlayerPassword(int playerId, PasswordChangeRequestDto passwordChangeRequestDto) {
        Optional<Player> playerOptional = playerRepository.findById(playerId);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();

            // 현재 비밀번호 확인
            if (!player.getPlayerPassword().equals(passwordChangeRequestDto.getCurrentPassword())) {
                return;  // 현재 비밀번호 불일치
            }

            // 새 비밀번호와 확인 비밀번호 일치 확인
            if (!passwordChangeRequestDto.getNewPassword().equals(passwordChangeRequestDto.getConfirmPassword())) {
                return;  // 새 비밀번호 불일치
            }

            // 비밀번호 업데이트
            player.changePassword(passwordEncoder.encode(passwordChangeRequestDto.getNewPassword()));
            playerRepository.save(player);
        }
    }

    public void updatePlayerInfo(int playerId, UpdatePlayerInfoDto newPlayerInfo) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("player not found"));

        player.changePassword(passwordEncoder.encode(newPlayerInfo.getPlayerPassword()));
        playerRepository.save(player);
    }

    public void deletePlayerById(int playerId) {
        playerRepository.deleteById(playerId);
    }

    public boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

}
