package ru.singularity.school.service;

import ru.singularity.school.model.Student;

import java.util.List;

public sealed interface StudentService permits StudentServiceImpl {
    // Get
    List<Student> getStudents(int age);

    List<Student> findStudentsByAgeBetween(int minAge, int maxAge);

    // Post
    Student addStudent(Student student);

    // Put
    Student updateStudent(Student student);

    // Delete
    Student delete(Long id);
}
