package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Table(name="attendances")
public class Attendance {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "attendance_id")
    private int attendanceId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "attendance_date")
    private LocalDate attendanceDate;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    @Column(name = "attendance_note")
    private String attendanceNote;
}
