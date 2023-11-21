package goorm.attendancebook.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "set")
public class AdminLoginResponseDto<D> {

    private boolean result;
    private String role;
    private String message;
    private D data;

    public static <D> AdminLoginResponseDto<D> setSuccess (String message, D data) {
        return AdminLoginResponseDto.set(true, "ROLE_ADMIN", message, data);
    }

    public static <D> AdminLoginResponseDto<D> setFailed (String message) {
        return AdminLoginResponseDto.set(false, null, message, null);
    }
}