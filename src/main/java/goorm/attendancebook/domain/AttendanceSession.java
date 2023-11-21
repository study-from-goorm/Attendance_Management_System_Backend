package goorm.attendancebook.domain;

import jakarta.persistence.*;
import lombok.Data;

@Table(name="attendance_sessions")
@Data
@Entity
public class AttendanceSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sessionId;

    @JoinColumn(name = "date_id", referencedColumnName = "dateId")
    private AttendanceDate attendanceDate;
    private int dateId;

    private int sessionOne;
    private int sessionTwo;
    private int sessionThree;
    private int sessionFour;
    private int sessionFive;
    private int sessionSix;
    private int sessionSeven;
    private int sessionEight;

    public AttendanceSession() {

    }

    public AttendanceSession(int sessionId, int dateId, int sessionOne, int sessionTwo, int sessionThree
            , int sessionFour, int sessionFive, int sessionSix, int sessionSeven, int sessionEight) {
        this.sessionId = sessionId;
        this.dateId = dateId;
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
