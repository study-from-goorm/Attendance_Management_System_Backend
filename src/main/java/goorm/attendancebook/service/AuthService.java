package goorm.attendancebook.service;

import goorm.attendancebook.domain.dao.AdminAccount;
import goorm.attendancebook.domain.dto.ResponseDto;
import goorm.attendancebook.domain.dto.SignInDto;
import goorm.attendancebook.domain.dto.SignInResponseDto;
import goorm.attendancebook.domain.dto.SignUpDto;
import goorm.attendancebook.repository.AdminRepository;
import goorm.attendancebook.repository.PlayerRepository;
import goorm.attendancebook.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;
    private final PlayerRepository playerRepository;
    private final TokenProvider tokenProvider;

    public ResponseDto<?> signUp(SignUpDto dto) {
        String adminPw = dto.getAdminPw();
        String adminPwCheck = dto.getAdminPwCheck();

        // 비밀번호가 서로 다르면 failed response 반환!
        if (!adminPw.equals((adminPwCheck)))
            return ResponseDto.setFailed("Password does not matched!");

        // AdminAccount 생성
        AdminAccount adminAccount = new AdminAccount(dto);

        // AdminRepository를 이용해서 데이터베이스에 Entity 저장!!
        try {
            adminRepository.save(adminAccount);
        } catch (Exception e) {
            return ResponseDto.setFailed("Data Base Error!");
        }

        return ResponseDto.setSuccess("Sign Up Success!", null);

    }

    public ResponseDto<SignInResponseDto> signIn (SignInDto dto) {
        String adminId = dto.getAdminId();
        String adminPw = dto.getAdminPw();
        try {
            boolean existed = adminRepository.existsByAdminIdAndAdminPw(adminId, adminPw);
            if (!existed) return ResponseDto.setFailed("Sign In Information Does Not Match");
        } catch (Exception e) {
            return ResponseDto.setFailed("Database Error");
        }
        AdminAccount adminAccount = null;
        try {
            adminAccount = adminRepository.findByAdminId(adminId).get();
        } catch (Exception e) {
            return ResponseDto.setFailed("Database Error");
        }
        adminAccount.setAdminPw("");

        String token = tokenProvider.create(adminId);
        int exprTime = 3600000;

        SignInResponseDto signInResponseDto = new SignInResponseDto(token, exprTime);
        return ResponseDto.setSuccess("Sign In Success", signInResponseDto);
    }

//    public boolean adminLoginService(String loginId, String loginPw) {
//        Optional<AdminAccount> admin = adminRepository.findByAdminId(loginId);
//        return admin.filter(adminAccount -> loginPw.equals(adminAccount.getAdminPw())).isPresent();
//    }
//    public boolean playerLoginService(String loginName, String loginPw) {
//        Optional<Player> player = playerRepository.findByPlayerName(loginName);
//        return player.filter(adminAccount -> loginPw.equals(adminAccount.getPlayerPw())).isPresent();
//    }
}
