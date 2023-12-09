package goorm.attendancemanagement.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter, setter, toString(), equals(), hashcode()를 자동으로 생성해준다.
@NoArgsConstructor // 매개변수가 없는 기본 생성자를 자동으로 생성한다.
@AllArgsConstructor // 클래스의 모든 필드를 매개변수로 하는 생성자를 자동으로 생성한다.

public class GetCourseDateDto {
    private String playerName;
    private Integer session_one;
    private Integer session_two;
    private Integer session_three;
    private Integer session_four;
    private Integer session_five;
    private Integer session_six;
    private Integer session_seven;
    private Integer session_eight;
}
