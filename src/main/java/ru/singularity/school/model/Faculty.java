package ru.singularity.school.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String color;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<Student> getStudents() {
        return students;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    // Utility method to add a student
    public void addStudent(Student student) {
        students.add(student);
        student.setFaculty(this);
    }

    // Utility method to remove a student
    public void removeStudent(Student student) {
        students.remove(student);
        student.setFaculty(null);
    }
}
