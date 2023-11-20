package goorm.attendancebook.repository;

import goorm.attendancebook.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findByPlayerId(Long playerId);

}
