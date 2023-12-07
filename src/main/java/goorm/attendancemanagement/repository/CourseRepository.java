package goorm.attendancemanagement.repository;

import goorm.attendancemanagement.domain.dao.Course;
import goorm.attendancemanagement.domain.dao.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    boolean existsByCourseName(String courseName);
}
