package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.NoArgsConstructor;

import java.time.LocalDate;
import lombok.Setter;


import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter @Setter
@NoArgsConstructor
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

    public Application(
            Player player,
            LocalDate applicationDate,
            LocalDate applicationTargetDate,
            ApplicationType applicationType,
            ApplicationStatus applicationStatus,
            String applicationReason) {

        this.player = player;
        this.applicationDate = applicationDate;
        this.applicationTargetDate = applicationTargetDate;
        this.applicationType = applicationType;
        this.applicationStatus = applicationStatus;
        this.applicationReason = applicationReason;
    }

    public void setApplicationStaus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    //==연관관계 메서드==//
    public void setPlayer (Player player){
        this.player = player;
        player.getApplication().add(this);

    }
}
