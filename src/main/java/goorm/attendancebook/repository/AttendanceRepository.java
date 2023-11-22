package goorm.attendancebook.repository;

import goorm.attendancebook.domain.dao.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    Attendance findByPlayerIdAndAttendanceDate(int playerId, LocalDate localDate);

    List<Attendance> findByPlayerIdAndAttendanceDateBetween(int playerId, LocalDate startDate, LocalDate endDate);

}
