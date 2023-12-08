package goorm.attendancemanagement.repository;

import goorm.attendancemanagement.domain.dao.Application;
import goorm.attendancemanagement.domain.dto.ApplicationRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ApplicatonRepository extends JpaRepository<Application, Integer> {

    List<Application> findAllByPlayer_playerId(int playerId);
    Optional<Application> findByPlayer_playerIdAndApplicationTargetDate(int playerId, LocalDate applicationTargetDate);

}
