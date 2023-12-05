package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name="admins")
public class Admin {

    @Id
    @Column(name = "admin_id")
    private String adminId;

    @Column(name = "admin_password")
    private String adminPassword;

    @Column(name = "role")
    private String role;


    public Admin() {
    }

    public Admin(String adminId, String adminPassword, String role) {
        this.adminId = adminId;
        this.adminPassword = adminPassword;
        this.role = role;
    }

}
