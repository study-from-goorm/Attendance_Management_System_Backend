package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class Application {

    @Id @GeneratedValue(strategy = IDENTITY)
    private int applicationId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    private LocalDateTime applicationWriteDate;

    private LocalDate applicationDate;

    @Enumerated(EnumType.STRING)
    private ApplicationType applicationType;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    private String applicationReason;
}
