package goorm.attendancemanagement.domain.dao;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class Player {

    @Id @GeneratedValue(strategy = IDENTITY)
    private int playerId;

    private String playerEmail;

    private String playerPassword;

    private String playerName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;

    public Player() {}
    public Player(String playerEmail, String playerPassword, String playerName, Course course) {
        this.playerEmail = playerEmail;
        this.playerPassword = playerPassword;
        this.playerName = playerName;
        this.course = course;
    }

    // 양방향 관계인 player와 (attendance, application)의 순환 참조 방지
    // player를 조회했을때 전체 출석일수와 전체 신청에 대한것이 나와야하므로

    @JsonIgnore
    @OneToMany(mappedBy = "player")
    private List<Attendance> attendance = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "player")
    private List<Application> application = new ArrayList<>();

}
