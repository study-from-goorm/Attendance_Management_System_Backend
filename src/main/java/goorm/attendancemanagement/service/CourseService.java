package goorm.attendancemanagement.service;

import goorm.attendancemanagement.domain.dao.Application;
import goorm.attendancemanagement.domain.dao.Course;
import goorm.attendancemanagement.domain.dao.Player;
import goorm.attendancemanagement.domain.dto.CreateCourseDto;
import goorm.attendancemanagement.domain.dto.GetApplicationDto;
import goorm.attendancemanagement.domain.dto.GetCoursesDto;
import goorm.attendancemanagement.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<GetCoursesDto> getCourses() {
        return courseRepository.findAll().stream()
                .map(course -> new GetCoursesDto(
                        course.getCourseId(),
                        course.getCourseName()
                ))
                .collect(Collectors.toList());
    }

    public void createCourse(String courseName, LocalDate startDate, LocalDate finishDate, int unitPeriod) {
        boolean exists = courseRepository.existsByCourseName(courseName);

        if (exists) {
            throw new IllegalArgumentException("코스 '" + courseName + "'가 이미 존재합니다.");
        }

        Course course = new Course(courseName, startDate, finishDate, unitPeriod);
        courseRepository.save(course);
    }

    public CreateCourseDto getCourseById(int courseId) {
        Course course = courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));

        return new CreateCourseDto(
                course.getCourseName(),
                course.getStartDate(),
                course.getFinishDate(),
                course.getUnitPeriod()
        );

    }

    public void updateCourse(int courseId, CreateCourseDto afterCourse) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));

        course.updateCourse(afterCourse);
        courseRepository.save(course);

    }

    public void deleteCourseById(int courseId) {
        courseRepository.deleteById(courseId);
    }

}

