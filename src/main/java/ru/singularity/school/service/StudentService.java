package ru.singularity.school.service;

import ru.singularity.school.model.Student;

import java.util.List;

public sealed interface StudentService permits StudentServiceImpl {
    // Get
    List<Student> getStudents(int age);

    // Post
    void addStudent(Student student);

    // Put
    void updateStudent(Student student);

    // Delete
    void delete(Long id);
}
