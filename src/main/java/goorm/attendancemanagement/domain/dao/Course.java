package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class Course {

    @Id @GeneratedValue(strategy = IDENTITY)
    private int courseId;

    private String courseName;

    public Course() {}

    public Course(String name) {
        this.courseName = name;
    }
}
