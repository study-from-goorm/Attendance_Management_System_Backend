package goorm.attendancemanagement.api;


import goorm.attendancemanagement.domain.dto.UpdatePlayerInfoDto;
import goorm.attendancemanagement.domain.dto.*;
import goorm.attendancemanagement.service.ApplicationService;
import goorm.attendancemanagement.service.AttendanceService;
import goorm.attendancemanagement.service.CourseService;
import goorm.attendancemanagement.service.PlayerService;
import goorm.attendancemanagement.upload.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminApiController {

    private final CourseService courseService;
    private final PlayerService playerService;
    private final ApplicationService applicationService;
    private final AttendanceService attendanceService;

    @GetMapping("/courses")
    public ResponseEntity<List<GetCoursesDto>> getCourses() {
        return ResponseEntity.ok(courseService.getCourses());
    }

    @GetMapping("/{courseId}/players")
    public ResponseEntity<List<GetPlayersByCourseDto>> getPlayers(@PathVariable("courseId") int courseId) {
        return ResponseEntity.ok(playerService.getPlayersByCourse(courseId));
    }

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestBody CreateCourseDto course) {
        try {
            courseService.createCourse(course.getCourseName(), course.getStartDate(), course.getFinishDate());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/players")
    public ResponseEntity<?> createPlayer(@RequestBody CreatePlayerDto player) {
        try {
            playerService.createPlayer(player);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<getCourseResponseDto> getCourse(@PathVariable("courseId") int courseId) {
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }

    @PatchMapping("/course/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable("courseId") int courseId, @RequestBody CreateCourseDto afterCourse) {
        courseService.updateCourse(courseId, afterCourse);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable("courseId") int courseId) {
        courseService.deleteCourseById(courseId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/player/{playerId}")
    public ResponseEntity<?> updatePlayer(@PathVariable("playerId") int playerId, @RequestBody UpdatePlayerInfoDto afterPlayer) {
        playerService.updatePlayerInfo(playerId, afterPlayer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/player/{playerId}")
    public ResponseEntity<?> deletePlayer(@PathVariable("playerId") int playerId) {
        playerService.deletePlayerById(playerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/applications")
    public ResponseEntity<List<GetApplicationsAllDto>> getApplicationAll() {
        return ResponseEntity.ok(applicationService.getApplicationsAll());

    }

    /**
     * 기존 dto에 application에 있는 파일 url 추가해야함
     * 서비스 수정시 파일이 null이라면 dto에 위치한 파일url에 null 입력해야함
     */
    @GetMapping("/applications/{applicationId}")
    public ResponseEntity<GetApplicationDto> getApplication(@PathVariable("applicationId") int applicationId) {
        return ResponseEntity.ok(applicationService.getApplicationById(applicationId));
    }

    /**
     * 파일 다운로드기능 메소드 구현 player에서 복붙해옴 문제 없나 재확인 필요함
     */
//    @GetMapping("/applications/{applicationId}/{fileName}")
//    public ResponseEntity<Resource> downloadFile(
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

    @PatchMapping("/applications/{applicationId}")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable("applicationId") int applicationId, @RequestBody UpdateApplicationStatusDto afterApplicationStatus) {
        applicationService.updateApplicationStatus(applicationId, afterApplicationStatus.getApplicationStatus());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/attendances/{courseId}/{date}")
    public ResponseEntity<GetCourseSessionByDateDto> updateSessionDetails(@PathVariable("courseId") int courseId, @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        GetCourseSessionByDateDto courseSessionByDate = attendanceService.getCourseSessionByDate(courseId, date);
        return ResponseEntity.ok(courseSessionByDate);
    }

    @PostMapping("/attendances/{courseId}/{date}")
    public ResponseEntity<GetCourseSessionByDateDto> updateSessionDetails(
            @PathVariable("courseId") int courseId, @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody GetCourseSessionByDateDto sessions) {
        GetCourseSessionByDateDto courseSessionByDate = attendanceService.updateCourseSessionByDate(courseId, date, sessions);
        return ResponseEntity.ok(courseSessionByDate);
    }
}
