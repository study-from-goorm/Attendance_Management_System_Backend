package goorm.attendancebook.web;

import goorm.attendancebook.domain.dto.AdminLoginDto;
import goorm.attendancebook.domain.dto.AdminLoginResponseDto;
import goorm.attendancebook.domain.dto.PlayerLoginDto;
import goorm.attendancebook.domain.dto.PlayerLoginResponseDto;
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
    public AdminLoginResponseDto<String> loginByAdminId(@RequestBody AdminLoginDto adminLoginDto) {
        return loginService.adminLoginService(adminLoginDto.getLoginId(), adminLoginDto.getLoginPw());
    }


    @PostMapping("/player")
    public PlayerLoginResponseDto<String> loginByPlayerName(@RequestBody PlayerLoginDto playerLoginDto) {
        return loginService.playerLoginService(playerLoginDto.getLoginEmail(), playerLoginDto.getLoginPw());
    }
}
