package goorm.attendancemanagement.repository;

import goorm.attendancemanagement.domain.dao.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    boolean existsByCourseName(String courseName);

    Optional<Course> findByCourseId(int courseIdId);
}
