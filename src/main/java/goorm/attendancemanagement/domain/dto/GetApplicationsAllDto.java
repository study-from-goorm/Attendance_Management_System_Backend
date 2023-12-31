package goorm.attendancemanagement.domain.dto;

import goorm.attendancemanagement.domain.dao.ApplicationStatus;
import goorm.attendancemanagement.domain.dao.ApplicationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetApplicationsAllDto {

    private int applicationId;
    private LocalDate applicationDate;
    private String playerName;
    private String courseName;
    private ApplicationType applicationType;
    private String applicationReason;
    private ApplicationStatus applicationStatus;

}
