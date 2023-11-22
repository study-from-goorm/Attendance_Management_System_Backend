package goorm.attendancebook.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginDto {
    @NotBlank
    private String loginId;
    @NotBlank
    private String loginPw;
}
