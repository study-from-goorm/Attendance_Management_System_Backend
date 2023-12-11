package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "Admins")
@Getter
public class Admin {

    @Id
    private String adminId;

    private String adminPassword;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Admin() {}

    public Admin(String adminId, String adminPassword, Role role) {
        this.adminId = adminId;
        this.adminPassword = adminPassword;
        this.role = role;
    }
}
