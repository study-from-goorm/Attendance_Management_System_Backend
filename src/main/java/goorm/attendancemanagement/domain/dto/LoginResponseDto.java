package goorm.attendancemanagement.domain.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String accessToken;
    private String role;
    private String playerId;
}
