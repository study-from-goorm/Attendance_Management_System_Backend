package goorm.attendancemanagement.repository;

import goorm.attendancemanagement.domain.dao.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface HolidayRepository extends JpaRepository<Holiday, LocalDate> {
}
