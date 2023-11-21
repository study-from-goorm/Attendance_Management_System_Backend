package goorm.attendancebook.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "set")
public class PlayerLoginResponseDto<D> {

    private boolean result;
    private Integer playerId;
    private String message;
    private D data;

    public static <D> PlayerLoginResponseDto<D> setSuccess (int playerId, String message, D data) {
        return PlayerLoginResponseDto.set(true, playerId, message, data);
    }

    public static <D> PlayerLoginResponseDto<D> setFailed (String message) {
        return PlayerLoginResponseDto.set(false, null, message, null);
    }
}