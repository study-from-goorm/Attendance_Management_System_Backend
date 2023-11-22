package goorm.attendancebook.web;


import goorm.attendancebook.domain.dao.Player;
import goorm.attendancebook.domain.dto.CourseSessionDto;
import goorm.attendancebook.domain.dto.PlayerSessionDto;
import goorm.attendancebook.domain.dto.ResponseDto;
import goorm.attendancebook.repository.AdminRepository;
import goorm.attendancebook.repository.AttendanceRepository;
import goorm.attendancebook.repository.PlayerRepository;
import goorm.attendancebook.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminRepository adminRepository;
    private final AttendanceRepository attendanceRepository;
    private final PlayerRepository playerRepository;
    private final PlayerService playerService;

    @GetMapping("/index")
    public ResponseDto<List<String>> getPlayerCourse() {
        return playerService.getAllCourse();
    }

    @GetMapping("/manage/{playerCourse}/{date}")
    public CourseSessionDto getSearchList(
            @PathVariable String playerCourse,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return playerService.getAllPlayerSessionsByCourseAndDate(playerCourse, date);
    }

    @PostMapping("/manage/{playerCourse}/{date}")
    public ResponseDto<?> updatePlayerAttendance(
            @PathVariable String playerCourse,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody CourseSessionDto courseSessionDto) {
        try {
            // 로직 구현: courseSessionDto에 담긴 정보로 attendance를 업데이트
            playerService.updatePlayerSessions(courseSessionDto);
            return ResponseDto.setSuccess("Attendance updated successfully", null);
        } catch (Exception e) {
            // 에러 처리
            return ResponseDto.setFailed("Failed to update attendance");
        }
    }


}
