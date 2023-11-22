package goorm.attendancebook.domain.dao;

import goorm.attendancebook.domain.dto.SignUpDto;
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

    public AdminAccount() {
    }

    public AdminAccount(SignUpDto dto) {
        this.adminId = dto.getAdminId();
        this.adminPw = dto.getAdminPw();
    }

}
