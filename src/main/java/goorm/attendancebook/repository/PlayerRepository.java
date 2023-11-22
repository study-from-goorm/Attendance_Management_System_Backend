package goorm.attendancebook.repository;

import goorm.attendancebook.domain.dao.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Player findByPlayerNameAndPlayerCourseAndPlayerPw(String playerName, String playerCourse, String playerPw);

    Optional<Player> findByPlayerEmail(String playerEmail);
}
