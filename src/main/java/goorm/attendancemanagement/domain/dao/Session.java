package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;

@Embeddable
@Getter
public class Session {

    private int sessionOne;
    private int sessionTwo;
    private int sessionThree;
    private int sessionFour;
    private int sessionFive;
    private int sessionSix;
    private int sessionSeven;
    private int sessionEight;

    protected Session() {
    }

    public Session(int sessionOne, int sessionTwo, int sessionThree, int sessionFour, int sessionFive, int sessionSix, int sessionSeven, int sessionEight) {
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
