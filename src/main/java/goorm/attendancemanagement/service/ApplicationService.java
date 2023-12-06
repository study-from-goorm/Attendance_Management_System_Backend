package goorm.attendancemanagement.service;

import goorm.attendancemanagement.domain.dao.Application;
import goorm.attendancemanagement.domain.dao.ApplicationStatus;
import goorm.attendancemanagement.domain.dao.ApplicationType;
import goorm.attendancemanagement.domain.dao.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApplicationService {

    @Autowired
    private EmailService emailService;

    public Application createApplication(Player player, ApplicationType applicationType, LocalDateTime date, String reason) {
        Application application = new Application();
        application.setPlayer(player);
        application.setApplicationType(applicationType);
        application.setApplicationStatus(ApplicationStatus.대기);
        application.setApplicationDate(date);
        application.setApplicationReason(reason);
        player.getApplication().add(application);

        notifyAdminForNewApplication(application); // 신청 생성 후 관리자에게 알림 전송

        return application;
    }

    public void cancelApplication(Application application) {
        switch (application.getApplicationStatus()) {
            case 대기:
                application.setApplicationStatus(ApplicationStatus.취소);
                notifyAdminForCancellation(application, "취소됨");
                break;

            case 승인:
                application.setApplicationStatus(ApplicationStatus.취소요청);
                notifyAdminForCancellation(application, "취소 요청됨");
                break;

            case 거절:
                throw new IllegalStateException("이미 거절된 상태입니다.");

            default:
                throw new IllegalStateException("처리할 수 없는 상태입니다.");
        }
    }

    private void notifyAdminForNewApplication(Application application) {
        String adminEmail = "admin@example.com"; // 관리자 이메일
        String message = "새로운 신청 알림: " + application.getPlayer().getPlayerName() + " - " + application.getApplicationType();

        // 이메일 전송 로직
        emailService.sendEmail(adminEmail, "새로운 신청 알림", message);
    }

    private void notifyAdminForCancellation(Application application, String statusMessage) {
        String adminEmail = "admin@example.com"; // 관리자 이메일
        String message = "신청 상태 변경 알림: " + statusMessage + " - " + application.getPlayer().getPlayerName();

        // 이메일 전송 로직
        emailService.sendEmail(adminEmail, "신청 상태 변경 알림", message);
    }
}
