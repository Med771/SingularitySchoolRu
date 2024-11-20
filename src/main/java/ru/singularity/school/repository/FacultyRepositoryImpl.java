package ru.singularity.school.repository;

import org.springframework.stereotype.Repository;
import ru.singularity.school.model.Faculty;

import java.util.HashMap;

// API data
@Repository
public final class FacultyRepositoryImpl implements FacultyRepository {
    // Init
    private final HashMap<Long, Faculty> faculties;
    private Long keyId;

    // Constructor
    public FacultyRepositoryImpl() {
        faculties = new HashMap<>();
        keyId = 0L;
    }

    // Get
    public HashMap<Long, Faculty> getFaculties() {
        return faculties;
    }

    // Post
    public void addFaculty(Faculty faculty) {
        faculties.put(keyId, faculty);
        keyId++;
    }

    // Put
    public void updateFaculty(Faculty faculty) {
        for (Long id : faculties.keySet()) {
            if (faculties.get(id).getId().equals(faculty.getId())) {
                faculties.put(id, faculty);
                return;
            }
        }

        faculties.put(keyId, faculty);
        keyId++;
    }

    // Delete
    public void deleteFaculty(Long id) {
        for (Long facultyId : faculties.keySet()) {
            if (faculties.get(facultyId).getId().equals(id)) {
                faculties.remove(facultyId);
            }
        }
    }
}
