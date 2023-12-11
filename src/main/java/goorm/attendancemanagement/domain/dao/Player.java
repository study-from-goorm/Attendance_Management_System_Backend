package goorm.attendancemanagement.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Table(name = "Players")
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

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public Player() {

    }

    public Player(String email, String pw, String name, Course course, Role role) {
        this.playerEmail = email;
        this.playerPassword = pw;
        this.playerName = name;
        this.course = course;
        this.role = role;
    }

    // 양방향 관계인 player와 (attendance, application)의 순환 참조 방지
    // player를 조회했을때 전체 출석일수와 전체 신청에 대한것이 나와야하므로

    @JsonIgnore
    @OneToMany(mappedBy = "player")
    private List<Attendance> attendance = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "player")
    private List<Application> application = new ArrayList<>();

    public void changePassword(String newPassword) {
        this.playerPassword = newPassword;
    }
}
