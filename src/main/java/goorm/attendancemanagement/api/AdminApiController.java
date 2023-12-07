package goorm.attendancemanagement.api;


import goorm.attendancemanagement.domain.dao.Course;
import goorm.attendancemanagement.domain.dao.Player;
import goorm.attendancemanagement.domain.dto.*;
import goorm.attendancemanagement.service.ApplicationService;
import goorm.attendancemanagement.service.CourseService;
import goorm.attendancemanagement.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminApiController {

    private final CourseService courseService;
    private final PlayerService playerService;
    private final ApplicationService applicationService;

    @GetMapping("/players")
    public ResponseEntity<List<String>> getCoursesAndPlayers() {
        return ResponseEntity.ok(courseService.getCourseNames());
    }

    @GetMapping("/{courseId}/players")
    public ResponseEntity<List<GetPlayersByCourseDto>> getPlayers(@PathVariable int courseId) {
        return ResponseEntity.ok(playerService.getPlayersByCourse(courseId));
    }

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestBody CreateCourseDto course) {
        try {
            Course newCourse = courseService.createCourse(course.getCourseName());
            return ResponseEntity.status(HttpStatus.CREATED).body(newCourse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/players")
    public ResponseEntity<?> createPlayer(@RequestBody CreatePlayerDto player) {
        try {
            Player newPlayer = playerService.createPlayer(player);
            return ResponseEntity.status(HttpStatus.CREATED).body(player);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/forms")
    public ResponseEntity<List<GetApplicationDto>> getApplicationAll() {
        return ResponseEntity.ok(applicationService.getApplicationsAll());

    }

    @GetMapping("/forms/{applicationId}")
    public ResponseEntity<GetApplicationDto> getApplication(@PathVariable int applicationId) {
        return ResponseEntity.ok(applicationService.getApplicationById(applicationId));

    }

    @PatchMapping("/forms/{applicationId}")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable int applicationId, @RequestBody UpdateApplicationStatusDto applicationStatus) {
        applicationService.updateApplicationStatus(applicationId, applicationStatus.getApplicationStatus());
        return ResponseEntity.ok().build();
    }
}
