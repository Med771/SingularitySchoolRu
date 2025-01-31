package ru.singularity.school.service;

import ru.singularity.school.dto.NewStudent;
import ru.singularity.school.model.Student;

import java.util.List;

public sealed interface StudentService permits StudentServiceImpl {
    // Get
    List<Student> getStudentsByAge(int age);

    List<Student> findStudentsByAgeBetween(int minAge, int maxAge);

    Integer getStudentsCount();

    Double getStudentsAverage();

    List<Student> getFiveLastStudents();

    List<String> getNamesStartingWithSymbol(String s);

    double getAverageAge();

    // Post
    Student addStudent(NewStudent newStudent);

    // Put
    Student updateStudent(Student student);

    // Delete
    Student delete(Long id);
}
