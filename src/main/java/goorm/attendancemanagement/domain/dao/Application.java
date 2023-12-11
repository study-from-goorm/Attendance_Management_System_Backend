package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

import java.time.LocalDate;


import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter @Setter
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

    public Application() {

    }

    //==연관관계 메서드==//
    public void setPlayer (Player player){
        this.player = player;
        player.getApplication().add(this);

    }
}
