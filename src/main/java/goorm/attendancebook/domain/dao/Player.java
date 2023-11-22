package goorm.attendancebook.domain.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name="players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private int playerId;

    @Column(name = "player_pw", length = 255, nullable = false)
    private String playerPw;

    @Column(name = "player_name", length = 20, nullable = false)
    private String playerName;

    @Column(name = "player_email", length = 100)
    private String playerEmail = "youremail@groom.io";

    @Column(name = "player_course", length = 20, nullable = false)
    private String playerCourse;

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY)
    private List<Attendance> attendances;

    public void updateAttendance(List<Integer> newAttendance) {
        // 특정 날짜의 Attendance 객체를 찾아서 업데이트하는 로직
        Attendance attendance = this.attendances.stream()
                .filter(a -> a.getAttendanceDate().equals(LocalDate.now())) // 또는 적절한 날짜 파라미터 사용
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Attendance record not found."));

        attendance.setSessionStatus(newAttendance);
    }

    public Player() {
    }

    public Player(String playerPw, String playerName, String playerEmail, String playerCourse) {
        this.playerPw = playerPw;
        this.playerName = playerName;
        this.playerEmail = (playerEmail != null) ? playerEmail : "youremail@groom.io";
        this.playerCourse = playerCourse;
    }

}
