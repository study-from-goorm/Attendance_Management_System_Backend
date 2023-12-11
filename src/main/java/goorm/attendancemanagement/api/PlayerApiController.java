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

    @GetMapping("/player/{playerId}/{month}")
    public ResponseEntity<AttendanceSummaryDto> playerAttendance(
            @PathVariable("playerId") int playerId,
            @PathVariable("month") int month) {
        AttendanceSummaryDto summary = attendanceService.getAttendanceSummary(playerId, month);
        return ResponseEntity.ok(summary);
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
