package goorm.attendancebook.web;

import goorm.attendancebook.domain.dao.Attendance;
import goorm.attendancebook.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceRepository attendanceRepository;

    @GetMapping("/find-by-date")
    public ResponseEntity<?> getAttendanceDate(@RequestParam LocalDate date) {
        List<Attendance> attendances = attendanceRepository.findByAttendanceDate(date);
        return ResponseEntity.ok(attendances);
    }


}
