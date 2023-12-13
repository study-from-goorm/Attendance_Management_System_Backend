package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@Table(name = "holidays")
public class Holiday {

    @Id @GeneratedValue(strategy = IDENTITY)
    private int holidayId;

    private LocalDate date;

    public Holiday() {}

    public Holiday(LocalDate date) {
        this.date = date;
    }
}
