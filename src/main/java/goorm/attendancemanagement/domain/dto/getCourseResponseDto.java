package goorm.attendancemanagement.domain.dto;

import goorm.attendancemanagement.domain.dao.UnitPeriod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class getCourseResponseDto {
    private String courseName;
    private LocalDate startDate;
    private LocalDate finishDate;
    private UnitPeriod unitPeriod;
}
