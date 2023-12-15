package goorm.attendancemanagement.domain.dao;

import goorm.attendancemanagement.upload.UploadFile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;


import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "Applications")
public class Application {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int applicationId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    private LocalDate applicationDate;

    private LocalDate applicationTargetDate;

    @Enumerated(EnumType.STRING)
    private ApplicationType applicationType;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    private String applicationReason;

    @Embedded
    private UploadFile uploadFile;

    public Application() {

    }

    public Application(
            Player player,
            LocalDate applicationDate,
            LocalDate applicationTargetDate,
            ApplicationType applicationType,
            ApplicationStatus applicationStatus,
            String applicationReason,
            UploadFile uploadFile) {

        this.player = player;
        this.applicationDate = applicationDate;
        this.applicationTargetDate = applicationTargetDate;
        this.applicationType = applicationType;
        this.applicationStatus = applicationStatus;
        this.applicationReason = applicationReason;
        this.uploadFile = uploadFile;
    }

    // UploadFile 없이 생성하는 생성자 (오버로딩)
    public Application(
            Player player,
            LocalDate applicationDate,
            LocalDate applicationTargetDate,
            ApplicationType applicationType,
            ApplicationStatus applicationStatus,
            String applicationReason) {

        this(player, applicationDate, applicationTargetDate, applicationType, applicationStatus, applicationReason, null);
    }
    //==연관관계 메서드==//
    public void setPlayer (Player player){
        this.player = player;
        player.getApplication().add(this);

    }
    public void updateApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}
