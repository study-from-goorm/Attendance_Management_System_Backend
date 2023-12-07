package goorm.attendancemanagement.service;

import goorm.attendancemanagement.domain.dao.Application;
import goorm.attendancemanagement.domain.dao.ApplicationStatus;
import goorm.attendancemanagement.domain.dto.GetApplicationDto;
import goorm.attendancemanagement.repository.ApplicationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public List<GetApplicationDto> getApplicationsAll() {
        return applicationRepository.findAllWithPlayerAndCourse().stream()
                .map(application -> new GetApplicationDto(
                        application.getApplicationDate(),
                        application.getApplicationTargetDate(),
                        application.getPlayer().getPlayerName(),
                        application.getPlayer().getCourse().getCourseName(),
                        application.getApplicationType(),
                        application.getApplicationReason(),
                        application.getApplicationStatus()
                ))
                .collect(Collectors.toList());
    }

    public GetApplicationDto getApplicationById(int applicationId) {
        Application application = applicationRepository.findByIdWithPlayerAndCourse(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));

        return new GetApplicationDto(
                application.getApplicationDate(),
                application.getApplicationTargetDate(),
                application.getPlayer().getPlayerName(),
                application.getPlayer().getCourse().getCourseName(),
                application.getApplicationType(),
                application.getApplicationReason(),
                application.getApplicationStatus());
    }

    public void updateApplicationStatus(int applicationId, ApplicationStatus applicationStatus) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));

        application.setApplicationStaus(applicationStatus);
        applicationRepository.save(application);
    }

}
