package goorm.attendancebook.repository;

import goorm.attendancebook.domain.AdminAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminAccount, Long> {

    Optional<AdminAccount> findByAdminId(String Id);
}
