package goorm.attendancebook.service;

import goorm.attendancebook.domain.dao.Player;
import goorm.attendancebook.domain.dto.AdminLoginResponseDto;
import goorm.attendancebook.domain.dto.PlayerLoginResponseDto;
import goorm.attendancebook.jwt.TokenProvider;
import goorm.attendancebook.repository.AdminRepository;
import goorm.attendancebook.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final AdminRepository adminRepository;
    private final PlayerRepository playerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;

    public AdminLoginResponseDto<String> adminLoginService(String loginId, String loginPw) {
        return (AdminLoginResponseDto<String>) adminRepository.findByAdminId(loginId)
                .map(admin -> {
                    if (bCryptPasswordEncoder.matches(loginPw, admin.getAdminPw())) {
                        // 성공 시, JWT 토큰 생성 및 반환
                        String token = tokenProvider.createAdminToken(admin.getAdminId(), admin.getAdminRole());
                        return AdminLoginResponseDto.setSuccess("Login successful", token);
                    } else {
                        // 비밀번호 불일치 시
                        return AdminLoginResponseDto.setFailed("Invalid password");
                    }
                })
                .orElse(AdminLoginResponseDto.setFailed("Admin not found")); // 관리자 ID가 존재하지 않을 때
    }


    public PlayerLoginResponseDto<String> playerLoginService(String loginEmail, String loginBirth) {
        return (PlayerLoginResponseDto<String>) playerRepository.findByPlayerEmail(loginEmail)
                .map(player -> {
                    if (bCryptPasswordEncoder.matches(loginBirth, player.getPlayerPw())) {
                        // 성공 시, JWT 토큰 생성 및 반환
                        String token = tokenProvider.createToken(player.getPlayerEmail());
                        return PlayerLoginResponseDto.setSuccess(player.getPlayerId(), "Login successful", token);
                    } else {
                        // 비밀번호 불일치 시
                        return PlayerLoginResponseDto.setFailed("Invalid password");
                    }
                })
                .orElse(PlayerLoginResponseDto.setFailed("Player not found")); // 플레이어가 존재하지 않을 때
    }


}
