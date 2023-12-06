package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class Attendance {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int attendanceId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    private LocalDate attendanceDate;

    @Embedded
    private Session session;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    private String attendanceNote;
}
