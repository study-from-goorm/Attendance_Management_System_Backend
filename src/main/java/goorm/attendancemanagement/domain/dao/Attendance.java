package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int attendanceId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    private LocalDate attendanceDate;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    private String attendanceNote;

    @Embedded
    private Session session;

    public Attendance(Player player, LocalDate attendanceDate, AttendanceStatus attendanceStatus, Session session) {
        this.player = player;
        this.attendanceDate = attendanceDate;
        this.attendanceStatus = attendanceStatus;
        this.session = session;
    }

    //==연관관계 메서드==//
    public void setPlayer(Player player) {
        this.player = player;
        player.getAttendance().add(this);
    }
    public void updateSessionAndStatus(Session session, AttendanceStatus status) {
        this.session = session;
        this.attendanceStatus = status;
    }
}
