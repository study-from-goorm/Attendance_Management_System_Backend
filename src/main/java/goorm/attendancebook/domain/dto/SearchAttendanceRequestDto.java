package goorm.attendancebook.domain.dto;


import lombok.Getter;

@Getter
public class SearchAttendanceRequestDto {
    private String name;
    private String course;
    private String pw;
    private String startDate;
    private String endDate;
}
