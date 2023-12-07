package goorm.attendancemanagement.service;

import goorm.attendancemanagement.domain.dao.Course;
import goorm.attendancemanagement.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<String> getCourseNames() {
        return courseRepository.findAll().stream()
                .map(Course::getCourseName)
                .collect(Collectors.toList());
    }

    public Course createCourse(String courseName) {
        boolean exists = courseRepository.existsByCourseName(courseName);

        if (exists) {
            throw new IllegalArgumentException("코스 '" + courseName + "'가 이미 존재합니다.");
        }

        Course course = new Course(courseName);
        return courseRepository.save(course);
    }
}

