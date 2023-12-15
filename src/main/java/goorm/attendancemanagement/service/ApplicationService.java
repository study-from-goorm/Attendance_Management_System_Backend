package goorm.attendancemanagement.service;

import goorm.attendancemanagement.domain.dao.Application;
import goorm.attendancemanagement.domain.dao.ApplicationStatus;
import goorm.attendancemanagement.domain.dto.*;
import goorm.attendancemanagement.repository.ApplicationRepository;
import goorm.attendancemanagement.repository.AttendanceRepository;
import goorm.attendancemanagement.upload.FileStore;
import goorm.attendancemanagement.upload.UploadFile;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import goorm.attendancemanagement.domain.dao.*;
import goorm.attendancemanagement.repository.PlayerRepository;

import java.io.IOException;
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
    private final AttendanceRepository attendanceRepository;
    private final FileStore fileStore;


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

    public ApplicationResponseDto createApplication(int playerId, ApplicationRequestDto requestDto) throws IOException {

        Optional<Application> existingApplication = applicationRepository.findByPlayer_playerIdAndApplicationTargetDate(playerId, requestDto.getApplicationTargetDate());
        if (existingApplication.isPresent()) {
            throw new IllegalStateException("해당 날짜에 이미 요청하신 신청이 존재합니다.");
        }

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + playerId));


        UploadFile uploadFile = null;
        if (requestDto.getFile() != null && !requestDto.getFile().isEmpty()) {
            uploadFile = fileStore.storeFile(requestDto.getFile());
        }

        Application application = new Application(
                player,
                LocalDate.now(),
                requestDto.getApplicationTargetDate(),
                ApplicationType.valueOf(requestDto.getApplicationType()),
                ApplicationStatus.대기,
                requestDto.getApplicationReason(),
                uploadFile // 파일 정보 추가
        );

        // 저장
        application = applicationRepository.save(application);

        // ApplicationResponseDto로 변환
        return new ApplicationResponseDto(
                application.getApplicationId(),
                application.getApplicationTargetDate(),
                application.getApplicationType().toString(),
                application.getApplicationStatus().toString(),
                application.getApplicationReason(),
                requestDto.getSessionName(),
                uploadFile != null ? uploadFile.getStoreFileName() : null
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
                        app.getApplicationId(),
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

    public void updateApplicationStatus(int applicationId, UpdateApplicationStatusDto applicationStatus) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));

        List<String> sessionName = applicationStatus.getSessionName();

        application.updateApplicationStatus(applicationStatus.getApplicationStatus());
        applicationRepository.save(application);

        if (applicationStatus.getApplicationStatus() == ApplicationStatus.승인) {
            Optional<Attendance> findAttendance = attendanceRepository.findByPlayerAndAttendanceDate(
                    application.getPlayer(),
                    application.getApplicationTargetDate()
            );
            Session session = new Session();
            if (applicationStatus.getApplicationType() == ApplicationType.공결) {
                session = convertSessionNamesToSession(sessionName, 6);

            } else if (applicationStatus.getApplicationType() == ApplicationType.휴가) {
                session = convertSessionNamesToSession(sessionName, 6);

            } else if (applicationStatus.getApplicationType() == ApplicationType.조퇴) {
                session = convertSessionNamesToSession(sessionName, 3);

            } else if (application.getApplicationType() == ApplicationType.외출) {
                session = convertSessionNamesToSession(sessionName, 4);
            }

            if (findAttendance.isPresent()) {
              throw new RuntimeException("이미 지난 날짜입니다.");
            } else {
            }
        } else if (applicationStatus.getApplicationStatus() == ApplicationStatus.거절) {
            Optional<Attendance> findAttendance = attendanceRepository.findByPlayerAndAttendanceDate(
                    application.getPlayer(),
                    application.getApplicationTargetDate()
            );

            if (findAttendance.isPresent()) {
                Attendance attendance = findAttendance.get();
                attendance.updateSessionAndStatus(new Session(), AttendanceStatus.notEntered);
                attendanceRepository.save(attendance);
            }
        }
    }

    public Session convertSessionNamesToSession(List<String> sessionNames, int value) {
        Session session = new Session(0,0,0,0,0,0,0,0);

        for (String sessionName : sessionNames) {
            switch (sessionName) {
                case "sessionOne":
                    session.setSessionOne(value);
                    break;
                case "sessionTwo":
                    session.setSessionTwo(value);
                    break;
                case "sessionThree":
                    session.setSessionThree(value);
                    break;
                case "sessionFour":
                    session.setSessionFour(value);
                    break;
                case "sessionFive":
                    session.setSessionFive(value);
                    break;
                case "sessionSix":
                    session.setSessionSix(value);
                    break;
                case "sessionSeven":
                    session.setSessionSeven(value);
                    break;
                case "sessionEight":
                    session.setSessionEight(value);
                    break;
            }
        }

        return session;
    }

    private String formatDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }
}
