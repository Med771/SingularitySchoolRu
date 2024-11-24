package ru.singularity.school.service;

import ru.singularity.school.model.Faculty;
import ru.singularity.school.model.Student;

import java.util.List;

public sealed interface FacultyService permits FacultyServiceImpl{
    // Get
    List<Faculty> getFaculties(String color);
    List<Student> getStudents(Long id);
    List<Faculty> findByFacultyName(String facultyName);
    List<Faculty> findByFacultyColor(String facultyColor);

    // Post
    void addFaculty(Faculty faculty);

    // Put
    void updateFaculty(Faculty faculty);

    // Delete
    void deleteFaculty(Long id);
}
