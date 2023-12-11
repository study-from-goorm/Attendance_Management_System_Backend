package goorm.attendancemanagement.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail() {
        String to = "jwjh1205@gmail.com"; // 수신자 이메일
        String subject = "테스트 이메일";
        String text = "이것은 테스트 이메일입니다.";

        emailService.sendEmail(to, subject, text);
    }
}
