package goorm.attendancebook.domain;

import jakarta.persistence.*;
import lombok.Data;

//@Table("players")
@Data
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;

    @Column(length = 20)
    private String playerName;

    @Column(length = 100)
    private String playerEmail;

    @Column(length = 20)
    private String playerCourse;

    public Player() {
    }

    public Player(Long playerId, String playerName, String playerEmail, String playerCourse) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerEmail = playerEmail;
        this.playerCourse = playerCourse;
    }

}
