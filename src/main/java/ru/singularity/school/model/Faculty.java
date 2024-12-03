package ru.singularity.school.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Faculty {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String color;

    @OneToMany(mappedBy = "faculty")
    private List<Student> students;

    // Get
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

    // Set
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
}
