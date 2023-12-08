package goorm.attendancemanagement.domain.dto;

import goorm.attendancemanagement.domain.dao.AttendanceStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlayerAttendanceDto {

    private String playerName;
    private LocalDate attendanceDate;
    private AttendanceStatus attendanceStatus;

    public PlayerAttendanceDto(String playerName, LocalDate attendanceDate, AttendanceStatus attendanceStatus) {
        this.playerName = playerName;
        this.attendanceDate = attendanceDate;
        this.attendanceStatus = attendanceStatus;
    }
}