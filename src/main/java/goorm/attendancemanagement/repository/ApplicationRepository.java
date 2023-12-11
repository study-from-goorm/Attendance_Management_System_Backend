package goorm.attendancemanagement.repository;

import goorm.attendancemanagement.domain.dao.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    @Query("SELECT a FROM Application a JOIN FETCH a.player p JOIN FETCH p.course")
    List<Application> findAllWithPlayerAndCourse();

    @Query("SELECT a FROM Application a JOIN FETCH a.player p JOIN FETCH p.course WHERE a.id = :id")
    Optional<Application> findByIdWithPlayerAndCourse(@Param("id") int id);
}
