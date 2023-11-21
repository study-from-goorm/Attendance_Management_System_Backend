package goorm.attendancebook.repository;

import goorm.attendancebook.domain.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionRepository extends JpaRepository<AttendanceSession, Integer> {
    List<AttendanceSession> findByDateId(int dateId);
}
