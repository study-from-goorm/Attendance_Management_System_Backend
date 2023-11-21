package goorm.attendancebook.repository;

import goorm.attendancebook.domain.dao.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    List<Player> findByPlayerId(int playerId);

    List<Player> findByPlayerCourse(String course);

    List<Player> findPlayerByPlayerNameAndPlayerEmailAndPlayerCourse(String playerName, String playerEmail, String playerCourse);

    Optional<Player> findByPlayerName(String playerName);

    Optional<Player> findByPlayerEmail(String playerEmail);
}
