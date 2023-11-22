package goorm.attendancebook.web;

import goorm.attendancebook.domain.dto.SearchAttendanceDetail;
import goorm.attendancebook.domain.dto.SearchAttendanceRequestDto;
import goorm.attendancebook.domain.dto.SearchAttendanceResponseDto;
import goorm.attendancebook.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/result")
    public SearchAttendanceResponseDto searchPlayer(@RequestBody SearchAttendanceRequestDto request) {
        return playerService.searchPlayer(request);
    }

    @GetMapping("result/detail")
    public SearchAttendanceDetail searchAttendanceDetail(@RequestParam int playerId, String date){
        return playerService.searchAttendanceDetail(playerId, date);
    }

}
