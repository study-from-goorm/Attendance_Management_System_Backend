package goorm.attendancemanagement.repository;

import goorm.attendancemanagement.domain.dao.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    boolean existsByPlayerEmail(String Email);
    Player findByPlayerEmail(String playerEmail);

    @Query("select p from Player p join fetch p.course where p.playerId = :id")
    List<Player> findByIdWithCourse(@Param("id") int id);

}
