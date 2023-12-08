package goorm.attendancemanagement.domain.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String id;
    private String password;
}
