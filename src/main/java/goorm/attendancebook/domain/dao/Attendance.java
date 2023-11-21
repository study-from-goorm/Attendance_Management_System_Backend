package goorm.attendancebook.domain.dao;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name="attendances")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private int attendanceId;

    @Column(name = "player_id")
    private int playerId;

    @Column(name = "attendance_date")
    private LocalDate attendanceDate; // 기본값은 JPA에서 설정하지 않음

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

    public Attendance() {
    }

    public Attendance(int attendanceId, int playerId, LocalDate attendanceDate, int sessionOne, int sessionTwo, int sessionThree, int sessionFour, int sessionFive, int sessionSix, int sessionSeven, int sessionEight) {
        this.attendanceId = attendanceId;
        this.playerId = playerId;
        this.attendanceDate = attendanceDate;
        this.sessionOne = sessionOne;
        this.sessionTwo = sessionTwo;
        this.sessionThree = sessionThree;
        this.sessionFour = sessionFour;
        this.sessionFive = sessionFive;
        this.sessionSix = sessionSix;
        this.sessionSeven = sessionSeven;
        this.sessionEight = sessionEight;
    }

}
