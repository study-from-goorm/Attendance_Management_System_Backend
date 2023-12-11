package goorm.attendancemanagement.repository;

import goorm.attendancemanagement.domain.dao.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {

    Admin findByAdminId(String adminId);

}
