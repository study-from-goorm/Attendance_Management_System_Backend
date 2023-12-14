package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@Table(name = "holidays")
public class Holiday {

    @Id
    private LocalDate date;

    private String dateName;

    public Holiday() {}

    public Holiday(LocalDate date, String dateName) {
        this.date = date;
        this.dateName = dateName;
    }
}
