package goorm.attendancemanagement.service;

import goorm.attendancemanagement.domain.dao.Course;
import goorm.attendancemanagement.domain.dao.UnitPeriod;
import goorm.attendancemanagement.domain.dto.CreateCourseDto;
import goorm.attendancemanagement.domain.dto.GetCoursesDto;
import goorm.attendancemanagement.domain.dto.getCourseResponseDto;
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

    public void createCourse(String courseName, LocalDate startDate, LocalDate finishDate) {
        boolean exists = courseRepository.existsByCourseName(courseName);

        if (exists) {
            throw new IllegalArgumentException("코스 '" + courseName + "'가 이미 존재합니다.");
        }

        UnitPeriod unitPeriod = calculateUnitPeriod(startDate, finishDate);

        Course course = new Course(courseName, startDate, finishDate, unitPeriod);
        courseRepository.save(course);
    }

    public UnitPeriod calculateUnitPeriod(LocalDate startDate, LocalDate finishDate) {
        int unitCount = Math.abs(finishDate.getMonthValue() - startDate.getMonthValue());
        if (finishDate.getDayOfMonth() >= startDate.getDayOfMonth()) {
            unitCount++;
        }

        LocalDate[] dates = new LocalDate[7];
        for (int i = 0; i < unitCount; i++) {
            dates[i] = startDate.plusMonths(i);
        }

        return new UnitPeriod(dates[0], dates[1], dates[2], dates[3], dates[4], dates[5], dates[6]);
    }

    public getCourseResponseDto getCourseById(int courseId) {
        Course course = courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));

        return new getCourseResponseDto(
                course.getCourseName(),
                course.getStartDate(),
                course.getFinishDate(),
                course.getUnitPeriod()
        );

    }

    public void updateCourse(int courseId, CreateCourseDto afterCourse) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));

        UnitPeriod unitPeriod = calculateUnitPeriod(afterCourse.getStartDate(), afterCourse.getFinishDate());

        course.updateCourse(afterCourse.getCourseName(), afterCourse.getStartDate(), afterCourse.getFinishDate(), unitPeriod);
        courseRepository.save(course);
    }

    public void deleteCourseById(int courseId) {
        courseRepository.deleteById(courseId);
    }

}

