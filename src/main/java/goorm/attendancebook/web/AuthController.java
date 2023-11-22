package goorm.attendancebook.web;

import goorm.attendancebook.domain.dto.ResponseDto;
import goorm.attendancebook.domain.dto.SignInDto;
import goorm.attendancebook.domain.dto.SignInResponseDto;
import goorm.attendancebook.domain.dto.SignUpDto;
import goorm.attendancebook.repository.AdminRepository;
import goorm.attendancebook.repository.PlayerRepository;
import goorm.attendancebook.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final PlayerRepository playerRepository;
    private final AdminRepository adminRepository;
    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseDto<?> signUp(@RequestBody SignUpDto requestBody) {
        ResponseDto<?> result = authService.signUp(requestBody);
        return result;
    }

    @PostMapping("/signIn")
    public ResponseDto<SignInResponseDto> signIn(@RequestBody SignInDto requestBody) {
        ResponseDto<SignInResponseDto> result = authService.signIn(requestBody);
        return result;
    }

//    @PostMapping("/admin")
//    public boolean loginByAdminId(String loginId, String loginPw) {
//        return authService.adminLoginService(loginId, loginPw);
//    }
//
//    @PostMapping("/player")
//    public boolean loginByPlayerName(String loginName, String loginPw) {
//        return authService.playerLoginService(loginName, loginPw);
//    }
}
