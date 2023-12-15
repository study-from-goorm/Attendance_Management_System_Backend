package goorm.attendancemanagement.domain.dto;

import goorm.attendancemanagement.domain.dao.ApplicationStatus;
import goorm.attendancemanagement.domain.dao.ApplicationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateApplicationStatusDto {
    private ApplicationStatus applicationStatus;
    private ApplicationType applicationType;
    private List<String> sessionName;
}
