package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter @Setter
public class Course {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;

    private String courseName;

}
