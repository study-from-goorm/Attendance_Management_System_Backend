package goorm.attendancemanagement.service;

import goorm.attendancemanagement.domain.dao.Application;
import goorm.attendancemanagement.domain.dao.ApplicationStatus;
import goorm.attendancemanagement.domain.dto.GetApplicationDto;
import goorm.attendancemanagement.domain.dto.GetApplicationsAllDto;
import goorm.attendancemanagement.repository.ApplicationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import goorm.attendancemanagement.domain.dao.*;
import goorm.attendancemanagement.domain.dto.ApplicationRequestDto;
import goorm.attendancemanagement.domain.dto.ApplicationResponseConfirmDto;
import goorm.attendancemanagement.domain.dto.ApplicationResponseDto;
import goorm.attendancemanagement.repository.PlayerRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final PlayerRepository playerRepository;

    public List<GetApplicationsAllDto> getApplicationsAll() {
        return applicationRepository.findAllWithPlayerAndCourse().stream()
                .map(application -> new GetApplicationsAllDto(
                        application.getApplicationId(),
                        application.getApplicationDate(),
                        application.getPlayer().getPlayerName(),
                        application.getPlayer().getCourse().getCourseName(),
                        application.getApplicationType(),
                        application.getApplicationReason(),
                        application.getApplicationStatus()))
                .collect(Collectors.toList());
    }

    public ApplicationResponseDto createApplication(int playerId, ApplicationRequestDto requestDto) {

        Optional<Application> existingApplication = applicationRepository.findByPlayer_playerIdAndApplicationTargetDate(playerId, requestDto.getApplicationTargetDate());
        if (existingApplication.isPresent()) {
            throw new IllegalStateException("해당 날짜에 이미 요청하신 신청이 존재합니다.");
        }

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + playerId));

        Application application = new Application(
                player,
                LocalDate.now(),
                requestDto.getApplicationTargetDate(),
                ApplicationType.valueOf(requestDto.getApplicationType()),
                ApplicationStatus.대기,
                requestDto.getApplicationReason()
        );

        // 저장
        application = applicationRepository.save(application);

        // ApplicationResponseDto로 변환
        return new ApplicationResponseDto(
                application.getApplicationId(),
                application.getApplicationTargetDate(),
                application.getApplicationType().toString(),
                application.getApplicationStatus().toString(),
                application.getApplicationReason()
        );
    }

    public void cancelApplication(Application application) {
        switch (application.getApplicationStatus()) {
            case 대기:
                application.updateApplicationStatus(ApplicationStatus.취소);
//                notifyAdminForCancellation(application, "취소됨");
                break;

            case 승인:
                application.updateApplicationStatus(ApplicationStatus.취소요청);
//                notifyAdminForCancellation(application, "취소 요청됨");
                break;

            case 거절:
                throw new IllegalStateException("이미 거절된 상태입니다.");

            default:
                throw new IllegalStateException("처리할 수 없는 상태입니다.");
        }
    }

    public List<ApplicationResponseConfirmDto> getPlayerApplications(int playerId) {
        List<Application> applications = applicationRepository.findAllByPlayer_playerId(playerId);
        return applications.stream()
                .map(app -> new ApplicationResponseConfirmDto(
                        formatDate(app.getApplicationDate()),
                        app.getApplicationTargetDate(),
                        app.getApplicationType().name(),
                        app.getApplicationReason(),
                        app.getApplicationStatus().name()
                ))
                .collect(Collectors.toList());
    }

    public GetApplicationDto getApplicationById(int applicationId) {
        Application application = applicationRepository.findByIdWithPlayerAndCourse(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));

        return new GetApplicationDto(
                application.getPlayer().getPlayerName(),
                application.getPlayer().getCourse().getCourseName(),
                application.getApplicationDate(),
                application.getApplicationTargetDate(),
                application.getApplicationType(),
                application.getApplicationReason(),
                application.getApplicationStatus());
    }

    public void updateApplicationStatus(int applicationId, ApplicationStatus applicationStatus) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));

        application.updateApplicationStatus(applicationStatus);
        applicationRepository.save(application);
    }

    private String formatDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }
}
