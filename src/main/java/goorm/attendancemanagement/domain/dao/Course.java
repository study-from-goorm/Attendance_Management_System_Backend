package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter @Setter
@Table(name = "Courses")
public class Course {

    @Id @GeneratedValue(strategy = IDENTITY)
    private int courseId;

    private String courseName;

    @OneToMany(mappedBy = "course")
    List<Player> players = new ArrayList<>();

    public Course() {}

    public Course(String name) {
        this.courseName = name;
    }

}
