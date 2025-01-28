package ru.singularity.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String color;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
        student.setFaculty(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.setFaculty(null);
    }
}
