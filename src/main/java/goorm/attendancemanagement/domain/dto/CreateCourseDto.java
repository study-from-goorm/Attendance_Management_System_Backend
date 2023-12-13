package goorm.attendancemanagement.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseDto {
    private String courseName;
    private LocalDate startDate;
    private LocalDate finishDate;
    private int unitPeriod;
}
