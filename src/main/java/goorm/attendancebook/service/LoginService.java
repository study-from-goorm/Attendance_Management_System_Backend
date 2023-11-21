package goorm.attendancebook.service;

import goorm.attendancebook.domain.AdminAccount;
import goorm.attendancebook.domain.Player;
import goorm.attendancebook.repository.AdminRepository;
import goorm.attendancebook.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final AdminRepository adminRepository;
    private final PlayerRepository playerRepository;

    public boolean adminLoginService(String loginId, String loginPw) {
        Optional<AdminAccount> admin = adminRepository.findByAdminId(loginId);
        return admin.filter(adminAccount -> loginPw.equals(adminAccount.getAdminPw())).isPresent();
    }
    public boolean playerLoginService(String loginName, String loginPw) {
        Optional<Player> player = playerRepository.findByPlayerName(loginName);
        return player.filter(adminAccount -> loginPw.equals(adminAccount.getPlayerPw())).isPresent();
    }
}
