package goorm.attendancemanagement.domain.dto;

import goorm.attendancemanagement.domain.dao.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCourseSessionByDateDto {

    private String courseName;
    private LocalDate date;

    private List<PlayerSessionsDto> playerSessions;

}