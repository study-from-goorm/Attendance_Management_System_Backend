package goorm.attendancemanagement.domain.dto;

import goorm.attendancemanagement.domain.dao.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDetailsDto {

    private LocalDate attendanceDate;
    private AttendanceStatus attendanceStatus;
}
