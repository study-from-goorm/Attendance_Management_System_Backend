package goorm.attendancebook.web;

import goorm.attendancebook.repository.AdminRepository;
import goorm.attendancebook.repository.PlayerRepository;
import goorm.attendancebook.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final PlayerRepository playerRepository;
    private final AdminRepository adminRepository;
    private final LoginService loginService;


    @PostMapping("/admin")
    public boolean loginByAdminId(String loginId, String loginPw) {
        return loginService.adminLoginService(loginId, loginPw);
    }

    @PostMapping("/player")
    public boolean loginByPlayerName(String loginName, String loginPw) {
        return loginService.playerLoginService(loginName, loginPw);
    }
}
