package goorm.attendancemanagement.api;

import goorm.attendancemanagement.domain.dto.*;
import goorm.attendancemanagement.service.ApplicationService;
import goorm.attendancemanagement.service.AttendanceService;
import goorm.attendancemanagement.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlayerApiController {

    private final ApplicationService applicationService;
    private final AttendanceService attendanceService;
    private final PlayerService playerService;

    @GetMapping("/player/{playerId}/{year}/{month}")
    public ResponseEntity<AttendanceSummaryDto> playerAttendance(
            @PathVariable("playerId") int playerId,
            @PathVariable("year") int year,
            @PathVariable("month") int month) {
        AttendanceSummaryDto summary = attendanceService.getAttendanceSummary(playerId, year, month);
        return ResponseEntity.ok(summary);
    }

    // 날짜를 클릭했을때 Day별 Session 나오게 하는 로직 추가
    @GetMapping("/player/{playerId}/{year}/{month}/{day}")
    public ResponseEntity<AttendanceSessionDto> playerSessionByDate(
            @PathVariable("playerId") int playerId,
            @PathVariable("year") int year,
            @PathVariable("month") int month,
            @PathVariable("day") int day) {
        AttendanceSessionDto sessionSummary = attendanceService.getSessionSummary(playerId, year, month, day);
        return ResponseEntity.ok(sessionSummary);
    }

    @PostMapping("/player/applications/{playerId}")
    public ResponseEntity<ApplicationResponseDto> playerApplication
            (@PathVariable("playerId") int playerId, @RequestBody ApplicationRequestDto requestDto) {
        ApplicationResponseDto response = applicationService.createApplication(playerId, requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/player/info/{playerId}")
    public ResponseEntity<PlayerInfoDto> getPlayerInfo(
            @PathVariable("playerId") int playerId) {
        PlayerInfoDto info = playerService.getPlayerInfo(playerId);
        return ResponseEntity.ok(info);
    }

    @PatchMapping("/player/info/{playerId}")
    public ResponseEntity<?> updatePlayerInfo(
            @PathVariable("playerId") int playerId, @RequestBody PasswordChangeRequestDto requestDto) {
        playerService.changePlayerPassword(playerId, requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/player/applications/{playerId}")
    public ResponseEntity<List<ApplicationResponseConfirmDto>> playerApplicaitionList(
            @PathVariable("playerId") int playerId) {
        List<ApplicationResponseConfirmDto> applicationDtos = applicationService.getPlayerApplications(playerId);
        return ResponseEntity.ok(applicationDtos);
    }


}
