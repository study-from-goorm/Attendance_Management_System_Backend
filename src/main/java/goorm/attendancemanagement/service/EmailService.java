package goorm.attendancemanagement.service;

import goorm.attendancemanagement.domain.dao.Admin;
import goorm.attendancemanagement.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final AdminRepository adminRepository;

    @Autowired
    public EmailService(JavaMailSender emailSender, AdminRepository adminRepository) {
        this.emailSender = emailSender;
        this.adminRepository = adminRepository;
    }

    public void sendEmail(String to, String subject, String text) {
        Admin admin = adminRepository.findAdmin(); // 적절한 메서드로 Admin 정보 조회
        if (admin != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(admin.getAdminEmail());
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        } else {
            log.error("Admin 정보를 찾을 수 없어 이메일을 보낼 수 없습니다");
        }
    }
}
