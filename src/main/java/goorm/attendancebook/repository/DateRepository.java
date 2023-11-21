package goorm.attendancebook.repository;

import goorm.attendancebook.domain.AttendanceDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DateRepository extends JpaRepository<AttendanceDate, Integer> {
    List<AttendanceDate> findByPlayerIdAndAttendanceDate(int playerId, LocalDate date);
}
