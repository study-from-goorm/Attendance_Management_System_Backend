package goorm.attendancemanagement.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequestDto {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}