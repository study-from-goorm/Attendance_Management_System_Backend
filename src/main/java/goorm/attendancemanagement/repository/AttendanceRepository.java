package goorm.attendancemanagement.repository;

import goorm.attendancemanagement.domain.dao.Attendance;
import goorm.attendancemanagement.domain.dto.PlayerAttendanceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    @Query("SELECT new goorm.attendancemanagement.domain.dto.PlayerAttendanceDto(a.player.playerName, a.attendanceDate, a.attendanceStatus) " +
            "FROM Attendance a JOIN a.player p WHERE p.playerId = :playerId AND MONTH(a.attendanceDate) = :month")
    List<PlayerAttendanceDto> findAttendanceInfoByPlayerIdAndMonth(@Param("playerId") int playerId, @Param("month") int month);



}
