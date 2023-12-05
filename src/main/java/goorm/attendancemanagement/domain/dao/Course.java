package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Table(name="courses")
public class Course {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "course_id")
    private int courseId;

    @Column(name = "course_name")
    private String courseName;

    public Course() {

    }

    public Course(String name) {
        this.courseName = name;
    }
}
