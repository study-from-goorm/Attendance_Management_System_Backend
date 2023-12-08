package goorm.attendancemanagement.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponseDto {

    private int applicationId;
    private LocalDate applicationTargetDate;
    private String applicationType;
    private String applicationStatus;
    private String applicationReason;
}