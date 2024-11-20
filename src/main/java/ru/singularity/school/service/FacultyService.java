package ru.singularity.school.service;

import ru.singularity.school.model.Faculty;

import java.util.List;

public sealed interface FacultyService permits FacultyServiceImpl{
    // Get
    List<Faculty> getFaculties(String color);

    // Post
    void addFaculty(Faculty faculty);

    // Put
    void updateFaculty(Faculty faculty);

    // Delete
    void deleteFaculty(Long id);
}
