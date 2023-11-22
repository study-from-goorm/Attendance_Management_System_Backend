package goorm.attendancebook.repository;

import goorm.attendancebook.domain.dao.Attendance;
import goorm.attendancebook.domain.dao.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    Attendance findByPlayerAndAttendanceDate(Optional<Player> player, LocalDate localDate);

    List<Attendance> findByPlayerAndAttendanceDateBetween(Player player, LocalDate startDate, LocalDate endDate);

}
