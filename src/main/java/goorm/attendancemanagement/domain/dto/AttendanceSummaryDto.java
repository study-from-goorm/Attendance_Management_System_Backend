package goorm.attendancemanagement.domain.dto;

import goorm.attendancemanagement.domain.dao.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceSummaryDto {
    private String playerName;
    private int month;
    private int totalDays;
    private Map<AttendanceStatus, Long> statusCount;
    private List<AttendanceDetailsDto> attendanceDetails;


}
