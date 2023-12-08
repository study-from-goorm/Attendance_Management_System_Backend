package goorm.attendancemanagement.repository;

import goorm.attendancemanagement.domain.dao.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {


}
