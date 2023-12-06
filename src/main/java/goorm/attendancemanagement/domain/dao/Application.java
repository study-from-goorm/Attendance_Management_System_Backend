package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter @Setter
public class Application {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int applicationId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    private LocalDateTime applicationDate;

    @Enumerated(EnumType.STRING)
    private ApplicationType applicationType;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    private String applicationReason;

    //==연관관계 메서드==//
    public void setPlayer(Player player) {
        this.player = player;
        player.getApplication().add(this);
    }
}
