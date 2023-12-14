package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.Embeddable;

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

    public Session() {
        this.sessionOne = 0;
        this.sessionTwo = 0;
        this.sessionThree = 0;
        this.sessionFour = 0;
        this.sessionFive = 0;
        this.sessionSix = 0;
        this.sessionSeven = 0;
        this.sessionEight = 0;
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
