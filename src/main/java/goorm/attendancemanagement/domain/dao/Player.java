package goorm.attendancemanagement.domain.dao;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter @Setter
public class Player {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int playerId;

    private String playerEmail;

    private String playerPassword;

    private String playerName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    // 양방향 관계인 player와 (attendance, application)의 순환 참조 방지
    // player를 조회했을때 전체 출석일수와 전체 신청에 대한것이 나와야하므로

    @JsonIgnore
    @OneToMany(mappedBy = "player")
    private List<Attendance> attendance = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "player")
    private List<Application> application = new ArrayList<>();

}
