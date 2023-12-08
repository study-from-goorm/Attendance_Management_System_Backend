package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "refreshToken")
public class RefreshToken {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "refreshToken")
    private String refreshToken;

    public RefreshToken() {
    }

    public RefreshToken(String id, String refreshToken) {
        this.id = id;
        this.refreshToken = refreshToken;
    }
}
