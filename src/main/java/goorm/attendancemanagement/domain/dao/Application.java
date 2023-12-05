package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Table(name="applications")
public class Application {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "application_id")
    private int applicationId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "application_date")
    private LocalDateTime applicationDate;

    @Column(name = "application_type")
    private ApplicationType applicationType;

    @Column(name = "application_status")
    private ApplicationStatus applicationStatus;

    @Column(name = "application_reason")
    private String applicationReason;
}
