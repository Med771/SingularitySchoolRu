package ru.singularity.school.repository;

import ru.singularity.school.model.Faculty;

import java.util.HashMap;

public sealed interface FacultyRepository permits FacultyRepositoryImpl {
    // Get
    HashMap<Long, Faculty> getFaculties();

    // Post
    void addFaculty(Faculty faculty);

    // Put
    void updateFaculty(Faculty faculty);

    // Remove
    void deleteFaculty(Long id);
}
