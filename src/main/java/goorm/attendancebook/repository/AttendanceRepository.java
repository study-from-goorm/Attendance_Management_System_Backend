package goorm.attendancebook.repository;

import goorm.attendancebook.domain.dao.Attendance;
import goorm.attendancebook.domain.dao.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    Attendance findByPlayerIdAndAttendanceDate(int playerId, LocalDate localDate);

    List<Attendance> findByPlayerIdAndAttendanceDateBetween(int playerId, LocalDate startDate, LocalDate endDate);

}
