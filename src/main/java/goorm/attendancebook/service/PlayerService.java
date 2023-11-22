package goorm.attendancebook.service;

import goorm.attendancebook.domain.dao.Attendance;
import goorm.attendancebook.domain.dao.Player;
import goorm.attendancebook.domain.dto.CourseSessionDto;
import goorm.attendancebook.domain.dto.PlayerSessionDto;
import goorm.attendancebook.domain.dto.ResponseDto;
import goorm.attendancebook.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public ResponseDto<List<String>> getAllCourse() {
        List<String> playerCourseList = new ArrayList<String>();

        try {
            playerCourseList = playerRepository.findDistinctPlayerCourses();
        } catch (Exception exception) {
            return ResponseDto.setFailed("Database Error");
        }

        return ResponseDto.setSuccess("Success", playerCourseList);
    }

    public CourseSessionDto getAllPlayerSessionsByCourseAndDate(String playerCourse, LocalDate date) {
        List<PlayerSessionDto> playerSessionList = new ArrayList<>();

        try {
            List<Object[]> sessions = playerRepository.findSessionsByCourseAndDate(playerCourse, date);
            for (Object[] record : sessions) {
                int playerId = (Integer) record[1];
                List<Integer> attendance = Arrays.asList(
                        (Integer) record[2],
                        (Integer) record[3],
                        (Integer) record[4],
                        (Integer) record[5],
                        (Integer) record[6],
                        (Integer) record[7],
                        (Integer) record[8],
                        (Integer) record[9]
                );
                playerSessionList.add(new PlayerSessionDto((String) record[0], playerId, attendance));
            }
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database Error", exception);
        }

        return new CourseSessionDto(playerCourse, date, playerSessionList);
    }



    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    @Transactional
    public ResponseDto<?> updatePlayerSessions(CourseSessionDto courseSessionDto) {
        try {
            for (PlayerSessionDto dto : courseSessionDto.getStudents()) {
                // playerId를 사용하여 Player 엔티티 찾기
                Player player = playerRepository.findById(dto.getPlayerId())
                        .orElseThrow(() -> new EntityNotFoundException("Player not found for ID: " + dto.getPlayerId()));

                // playerCourse를 검사
                if (!player.getPlayerCourse().equals(courseSessionDto.getCourse())) {
                    throw new IllegalStateException("Player course does not match.");
                }

                // 특정 날짜의 Attendance 엔티티를 업데이트합니다.
                Attendance attendance = player.getAttendances().stream()
                        .filter(a -> a.getAttendanceDate().equals(courseSessionDto.getDate()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("Attendance record not found for date: " + courseSessionDto.getDate()));

                // Attendance 엔티티를 업데이트 합니다.
                attendance.setSessionStatus(dto.getAttendance());

                // 변경된 Attendance 정보 저장
                playerRepository.save(player);
            }
            return ResponseDto.setSuccess("Attendance updated successfully", null);
        } catch (Exception exception) {
            // 예외 처리 로직
            return ResponseDto.setFailed("Failed to update attendance");
        }
    }
}