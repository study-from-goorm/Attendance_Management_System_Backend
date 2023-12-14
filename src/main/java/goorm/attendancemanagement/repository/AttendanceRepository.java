package goorm.attendancemanagement.repository;

import goorm.attendancemanagement.domain.dao.Attendance;
import goorm.attendancemanagement.domain.dao.Player;
import goorm.attendancemanagement.domain.dto.PlayerAttendanceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    @Query("SELECT new goorm.attendancemanagement.domain.dto.PlayerAttendanceDto(a.player.playerName, a.attendanceDate, a.attendanceStatus) " +
            "FROM Attendance a JOIN a.player p WHERE p.playerId = :playerId AND YEAR(a.attendanceDate) = :year AND MONTH(a.attendanceDate) = :month")
    List<PlayerAttendanceDto> findAttendanceInfoByPlayerIdAndMonth(@Param("playerId") int playerId, @Param("year") int year, @Param("month") int month);

    @Query("SELECT a FROM Attendance a " +
            " WHERE a.player.playerId = :playerId AND YEAR(a.attendanceDate) = :year AND MONTH(a.attendanceDate) = :month AND DAY(a.attendanceDate) = :day")
    Optional<Attendance> findSessionInfoByPlayerIdAndDay(@Param("playerId") int playerId, @Param("year") int year, @Param("month") int month, @Param("day") int day);

    boolean existsByPlayerAndAttendanceDate(Player player, LocalDate date);

    @Query("SELECT a, a.player.playerName, a.player.course.courseName FROM Attendance a" +
            " JOIN FETCH a.player p WHERE a.player.course.courseId = :courseId AND a.attendanceDate = :date")
    List<Attendance> findPlayerSessionFromCourseInfo(@Param("courseId") int courseId, @Param("date") LocalDate date);

    Optional<Attendance> findAllByPlayerAndAttendanceDate(Player player, LocalDate date);

}
