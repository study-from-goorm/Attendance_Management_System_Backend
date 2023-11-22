package goorm.attendancebook.repository;

import goorm.attendancebook.domain.dao.AdminAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminAccount, Long> {


    Optional<AdminAccount> findByAdminId(String Id);

    Boolean existsByAdminIdAndAdminPw(String adminId, String adminPw);
}
