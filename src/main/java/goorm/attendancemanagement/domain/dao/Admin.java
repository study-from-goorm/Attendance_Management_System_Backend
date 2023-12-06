package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Admin {

    @Id
    private String adminId;

    private String adminEmail;

    private String adminPassword;

    @Enumerated(EnumType.STRING)
    private Role role;

}
