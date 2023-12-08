package goorm.attendancemanagement.domain.dao;


import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Table(name="players")
public class Player {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "player_id")
    private int playerId;

    @Column(name = "player_email")
    private String playerEmail;

    @Column(name = "player_password")
    private String playerPassword;

    @Column(name = "player_name")
    private String playerName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "role")
    private String role;

    public Player() {

    }

    public Player(String email, String pw, String name, Course course, String role) {
        this.playerEmail = email;
        this.playerPassword = pw;
        this.playerName = name;
        this.course = course;
        this.role = role;
    }

}
