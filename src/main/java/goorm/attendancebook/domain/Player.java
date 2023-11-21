package goorm.attendancebook.domain;

import jakarta.persistence.*;
import lombok.Data;

@Table(name="players")
@Data
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int playerId;

    @Column(length = 255)
    private String playerPw;

    @Column(length = 20)
    private String playerName;

    @Column(length = 100)
    private String playerEmail;

    @Column(length = 20)
    private String playerCourse;

    public Player() {
    }

    public Player(int playerId, String playerPw, String playerName, String playerEmail, String playerCourse) {
        this.playerId = playerId;
        this.playerPw = playerPw;
        this.playerName = playerName;
        this.playerEmail = playerEmail;
        this.playerCourse = playerCourse;
    }

}
