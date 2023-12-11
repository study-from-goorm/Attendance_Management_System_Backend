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

    @Column(name = "attendance_status")
    private AttendanceStatus attendanceStatus;

    @Column(name = "session_one", columnDefinition = "int default 0")
    private int sessionOne = 0;

    @Column(name = "session_two", columnDefinition = "int default 0")
    private int sessionTwo = 0;

    @Column(name = "session_three", columnDefinition = "int default 0")
    private int sessionThree = 0;

    @Column(name = "session_four", columnDefinition = "int default 0")
    private int sessionFour = 0;

    @Column(name = "session_five", columnDefinition = "int default 0")
    private int sessionFive = 0;

    @Column(name = "session_six", columnDefinition = "int default 0")
    private int sessionSix = 0;

    @Column(name = "session_seven", columnDefinition = "int default 0")
    private int sessionSeven = 0;

    @Column(name = "session_eight", columnDefinition = "int default 0")
    private int sessionEight = 0;

    @Column(name = "attendance_note")
    private String attendanceNote;
}
