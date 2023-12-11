package goorm.attendancemanagement.repository;

import goorm.attendancemanagement.domain.dao.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    boolean existsByCourseName(String courseName);
}
