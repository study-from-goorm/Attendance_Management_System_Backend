package goorm.attendancebook.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerLoginDto {
    @NotBlank
    private String loginCourse;
    @NotBlank
    private String loginEmail;
    @NotBlank
    private String loginBirth;
}
