package goorm.attendancebook.domain;

import jakarta.persistence.*;
import lombok.Data;

@Table(name="admins")
@Data
@Entity
public class AdminAccount {

    @Id
    @Column(length = 20)
    private String adminId;

    @Column(length = 255)
    private String adminPw;

    public AdminAccount() {
    }

    public AdminAccount(String adminId, String adminPw) {
        this.adminId = adminId;
        this.adminPw = adminPw;
    }
}
