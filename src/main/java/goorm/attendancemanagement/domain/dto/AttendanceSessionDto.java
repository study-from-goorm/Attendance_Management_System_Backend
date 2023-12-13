package goorm.attendancemanagement.domain.dto;

import goorm.attendancemanagement.domain.dao.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceSessionDto {

    private int year;
    private int month;
    private int day;
    private List<Session> sessionList;
}
