package goorm.attendancemanagement.api;

import goorm.attendancemanagement.domain.dto.*;
import goorm.attendancemanagement.service.ApplicationService;
import goorm.attendancemanagement.service.AttendanceService;
import goorm.attendancemanagement.service.PlayerService;
import goorm.attendancemanagement.upload.FileStore;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    @GetMapping("/player/{playerId}/info")
    public ResponseEntity<PlayerInfoDto> getPlayerInfo(
            @PathVariable("playerId") int playerId) {
        PlayerInfoDto info = playerService.getPlayerInfo(playerId);
        return ResponseEntity.ok(info);
    }

    @PatchMapping("/player/{playerId}/info")
    public ResponseEntity<?> updatePlayerInfo(
            @PathVariable("playerId") int playerId, @RequestBody PasswordChangeRequestDto requestDto) {
        playerService.changePlayerPassword(playerId, requestDto);
        return ResponseEntity.ok().build();
    }

    // 2023.12.15 파일업로드 기능추가
    @PostMapping("/player/{playerId}/applications")
    public ResponseEntity<ApplicationResponseDto> playerApplication
            (@PathVariable("playerId") int playerId, @RequestBody ApplicationRequestDto requestDto) throws IOException {
        ApplicationResponseDto response = applicationService.createApplication(playerId, requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/player/{playerId}/applications")
    public ResponseEntity<List<ApplicationResponseConfirmDto>> playerApplicationList(
            @PathVariable("playerId") int playerId) {
        List<ApplicationResponseConfirmDto> applicationDtos = applicationService.getPlayerApplications(playerId);
        return ResponseEntity.ok(applicationDtos);
    }

    /**
     * 위에 application List중 하나를 선택했을때 디테일 페이지 - dto랑 매개변수 리턴값 수정해야함 모든 요청 디테일하게 보고하는 창, dto에 파일 url추가되어야함
     * 서비스에서 파일이 null이라면 파일url이 안뜨게 해야됨(null입력)
     */
//    @GetMapping("/player/{playerId}/applications/{applicationId}")
//    public ResponseEntity<List<ApplicationResponseConfirmDto>> playerApplicationDetail(
//            @PathVariable("playerId") int playerId, @PathVariable("applicationId") int applicationId) {
//        List<ApplicationResponseConfirmDto> applicationDtos = applicationService.getPlayerApplications(playerId);
//        return ResponseEntity.ok(applicationDtos);
//    }

    // 파일 다운로드기능 메소드 구현
//    @GetMapping("/player/{playerId}/applications/{applicationId}/files/{fileName}")
//    public ResponseEntity<Resource> downloadFile(
//            @PathVariable("playerId") int playerId,
//            @PathVariable("applicationId") int applicationId,
//            @PathVariable("fileName") String fileName,
//            @Autowired FileStore fileStore) throws IOException {
//
//        String fullPath = fileStore.getFullPath(fileName);
//        Resource resource = new UrlResource("file:" + fullPath);
//
//        if (!resource.exists() || !resource.isReadable()) {
//            throw new FileNotFoundException("파일을 찾을 수 없습니다: " + fileName);
//        }
//
//        String uploadFileName = StringUtils.getFilename(resource.getFilename());
//        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
//        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
//                .body(resource);
//    }

    /**
     * Application List -> Application Details -> 취소신청 하는 메서드 구현 중
     */
//    @PatchMapping("/player/{playerId}/applications/{applicationId}")
//    public ResponseEntity<List<ApplicationResponseConfirmDto>> playerApplicationRequestCancel(
//            @PathVariable("playerId") int playerId, @PathVariable("applicationId") int applicationId, RequestBody requestBody) {
//        List<ApplicationResponseConfirmDto> applicationDtos = applicationService.getPlayerApplications(playerId);
//        return ResponseEntity.ok(applicationDtos);
//    }
}
