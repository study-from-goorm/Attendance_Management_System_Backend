package goorm.attendancebook.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchAttendanceResponseDto {
    private String name;
    private int playerId;
    private String course;
    private int total;
    private int state1;
    private int state2;
    private int state3;
    private int state4;
    private int state5;
    private int state6;
    private int[] stateArr;

    @Builder
    public SearchAttendanceResponseDto(String name, int playerId, String course, int total, int state1, int state2, int state3, int state4, int state5, int state6, int[] stateArr) {
        this.name = name;
        this.playerId = playerId;
        this.course = course;
        this.total = total;
        this.state1 = state1;
        this.state2 = state2;
        this.state3 = state3;
        this.state4 = state4;
        this.state5 = state5;
        this.state6 = state6;
        this.stateArr = stateArr;
    }

}