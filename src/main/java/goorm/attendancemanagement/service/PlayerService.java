package goorm.attendancemanagement.service;

import goorm.attendancemanagement.domain.dao.Player;
import goorm.attendancemanagement.domain.dto.PasswordChangeRequestDto;
import goorm.attendancemanagement.domain.dto.PlayerInfoDto;
import goorm.attendancemanagement.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerInfoDto getPlayerInfo(int playerId) {
        return playerRepository.findById(playerId)
                .map(player -> new PlayerInfoDto(
                        player.getCourse().getCourseName(),
                        player.getPlayerEmail(),
                        player.getPlayerName()))
                .orElse(null);  // 플레이어가 없을 경우 null 반환
    }
    public boolean changePlayerPassword(int playerId, PasswordChangeRequestDto passwordChangeRequestDto) {
        Optional<Player> playerOptional = playerRepository.findById(playerId);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();

            // 현재 비밀번호 확인
            if (!player.getPlayerPassword().equals(passwordChangeRequestDto.getCurrentPassword())) {
                return false;  // 현재 비밀번호 불일치
            }

            // 새 비밀번호와 확인 비밀번호 일치 확인
            if (!passwordChangeRequestDto.getNewPassword().equals(passwordChangeRequestDto.getConfirmPassword())) {
                return false;  // 새 비밀번호 불일치
            }

            // 비밀번호 업데이트
            player.setPlayerPassword(passwordChangeRequestDto.getNewPassword());
            playerRepository.save(player);
            return true;
        }
        return false;
    }
}
