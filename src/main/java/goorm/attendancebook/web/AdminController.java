package goorm.attendancebook.web;


import goorm.attendancebook.domain.dao.Player;
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
    public ResponseDto<List<PlayerSessionDto>> getSearchList(
            @PathVariable String playerCourse,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return playerService.getAllPlayerSessionsByCourseAndDate(playerCourse, date);
    }



}
