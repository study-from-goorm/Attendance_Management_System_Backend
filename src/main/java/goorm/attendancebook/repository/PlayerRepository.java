package goorm.attendancebook.repository;

import goorm.attendancebook.domain.dao.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    List<Player> findByPlayerId(int playerId);

    @Query("SELECT DISTINCT p.playerCourse FROM Player p WHERE p.playerCourse IS NOT NULL")
    List<String> findDistinctPlayerCourses();

    @Query("SELECT p.playerName, p.playerId, a.sessionOne, a.sessionTwo, a.sessionThree, a.sessionFour, a.sessionFive, a.sessionSix, a.sessionSeven, a.sessionEight " +
            "FROM Player p JOIN p.attendances a " +
            "WHERE p.playerCourse = :playerCourse AND a.attendanceDate = :date")
    List<Object[]> findSessionsByCourseAndDate(@Param("playerCourse") String playerCourse, @Param("date") LocalDate date);

    List<Player> findByPlayerCourse(String course);

    List<Player> findPlayerByPlayerNameAndPlayerEmailAndPlayerCourse(String playerName, String playerEmail, String playerCourse);

    Optional<Player> findByPlayerName(String playerName);
}
