package goorm.attendancemanagement.domain.dto;

import goorm.attendancemanagement.domain.dao.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerSessionsDto {

    private String playerName;
    private Session sessionList;
}
