package goorm.attendancemanagement.domain.dto;

import goorm.attendancemanagement.domain.dao.ApplicationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRequestDto {

    private LocalDate applicationTargetDate;
    private String applicationType;
    private String applicationReason;
}
