package ru.singularity.school.repository;

import ru.singularity.school.model.Student;

import java.util.HashMap;

public sealed interface StudentRepository permits StudentRepositoryImpl {
    // Get
    HashMap<Long, Student> getStudents();

    // Post
    void addStudent(Student student);

    // Put
    void updateStudent(Student student);

    // Delete
    void deleteStudent(Long id);
}
