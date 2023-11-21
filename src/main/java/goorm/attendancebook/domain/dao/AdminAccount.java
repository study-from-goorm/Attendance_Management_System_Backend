package goorm.attendancebook.domain.dao;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="admins")
public class AdminAccount {

    @Id
    @Column(name = "admin_id", length = 20, nullable = false)
    private String adminId;

    @Column(name = "admin_pw", length = 255, nullable = false)
    private String adminPw;

    @Column(name = "admin_role", length = 20, nullable = false)
    private String adminRole;
    public AdminAccount() {
    }

    public AdminAccount(String adminId, String adminPw, String adminRole) {
        this.adminId = adminId;
        this.adminPw = adminPw;
        this.adminRole = adminRole;
    }

}
