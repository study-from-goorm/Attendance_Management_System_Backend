package goorm.attendancemanagement.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayersByCourseDto {
    private String courseName;
    private int playerId;
    private String playerName;
    private String playerEmail;
}
