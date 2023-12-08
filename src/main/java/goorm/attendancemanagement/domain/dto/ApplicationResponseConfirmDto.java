package goorm.attendancemanagement.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponseConfirmDto {

    private String applicationDate;
    private LocalDate applicationTargetDate;
    private String applicationType;
    private String applicationReason;
    private String applicationStatus;
}
