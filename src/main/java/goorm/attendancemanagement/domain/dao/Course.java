package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Table(name = "Courses")
public class Course {

    @Id @GeneratedValue(strategy = IDENTITY)
    private int courseId;

    private String courseName;

    private LocalDate startDate;

    private LocalDate finishDate;

    @Embedded
    private UnitPeriod unitPeriod;

    @OneToMany(mappedBy = "course")
    List<Player> players = new ArrayList<>();

    public Course() {}

    public Course(String name, LocalDate startDate, LocalDate finishDate, UnitPeriod unitPeriod) {
        this.courseName = name;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.unitPeriod = unitPeriod;
    }

    public void updateCourse(String name, LocalDate startDate, LocalDate finishDate, UnitPeriod unitPeriod) {
        this.courseName = name;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.unitPeriod = unitPeriod;
    }
}