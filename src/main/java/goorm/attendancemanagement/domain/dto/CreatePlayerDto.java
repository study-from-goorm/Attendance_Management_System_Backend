package goorm.attendancemanagement.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlayerDto {
    private String playerEmail;
    private String playerPassword;
    private String playerName;
    private int courseId;
}
