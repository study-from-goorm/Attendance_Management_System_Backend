package goorm.attendancebook.repository;

import goorm.attendancebook.domain.dao.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
}
