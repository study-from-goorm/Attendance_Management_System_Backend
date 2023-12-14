package goorm.attendancemanagement.service;

import goorm.attendancemanagement.domain.dao.*;
import goorm.attendancemanagement.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void createAdmin(String adminId, String adminPassword) {
        Admin newAdmin = new Admin(adminId, passwordEncoder.encode(adminPassword), Role.ROLE_ADMIN);
        adminRepository.save(newAdmin);
    }
}
