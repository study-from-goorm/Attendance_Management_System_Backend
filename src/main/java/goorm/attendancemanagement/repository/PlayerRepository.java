package goorm.attendancemanagement.repository;
import goorm.attendancemanagement.domain.dao.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, String> {

    boolean existsByPlayerEmail(String Email);
    Player findByPlayerEmail(String playerEmail);
}
