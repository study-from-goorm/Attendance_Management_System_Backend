package goorm.attendancebook.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchAttendanceDetail {
    private int attendanceId;
    private int sessionOne;
    private int sessionTwo;
    private int sessionThree;
    private int sessionFour;
    private int sessionFive;
    private int sessionSix;
    private int sessionSeven;
    private int sessionEight;

    @Builder
    public SearchAttendanceDetail(int attendanceId, int sessionOne, int sessionTwo, int sessionThree, int sessionFour, int sessionFive, int sessionSix, int sessionSeven, int sessionEight){
        this.attendanceId = attendanceId;
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
