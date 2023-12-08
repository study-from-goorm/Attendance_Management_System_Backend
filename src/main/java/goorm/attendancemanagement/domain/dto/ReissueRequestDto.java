package goorm.attendancemanagement.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ReissueRequestDto {
    @NotEmpty(message = "accessToken 을 입력해주세요.")
    private String accessToken;

    @NotEmpty(message = "refreshToken 을 입력해주세요.")
    private String refreshToken;
}
