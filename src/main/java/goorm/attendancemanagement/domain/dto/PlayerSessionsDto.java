package goorm.attendancemanagement.domain.dto;

import goorm.attendancemanagement.domain.dao.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerSessionsDto {

    private Integer playerId;
    private String playerName;
    private Session sessionList;
}
