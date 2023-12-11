package goorm.attendancemanagement.domain.dto;

import goorm.attendancemanagement.domain.dao.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateApplicationStatusDto {
    private ApplicationStatus applicationStatus;
}
