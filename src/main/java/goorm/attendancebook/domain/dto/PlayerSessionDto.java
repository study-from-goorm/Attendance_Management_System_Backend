package goorm.attendancebook.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerSessionDto {

    private String playerName;
    private int playerId;
    private List<Integer> attendance; // 세션 정보를 저장할 리스트

    // DTO 객체를 생성할 때 세션 정보를 리스트로 변환하는 생성자
    public PlayerSessionDto(String playerName, int playerId, int sessionOne, int sessionTwo, int sessionThree, int sessionFour, int sessionFive, int sessionSix, int sessionSeven, int sessionEight) {
        this.playerName = playerName;
        this.playerId = playerId;
        this.attendance = Arrays.asList(sessionOne, sessionTwo, sessionThree, sessionFour, sessionFive, sessionSix, sessionSeven, sessionEight);
    }
}
