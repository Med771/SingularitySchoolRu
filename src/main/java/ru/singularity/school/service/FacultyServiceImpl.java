package ru.singularity.school.service;

import org.springframework.stereotype.Service;
import ru.singularity.school.model.Faculty;
import ru.singularity.school.repository.FacultyRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

@Service
public final class FacultyServiceImpl implements FacultyService {
    // Init
    private final FacultyRepositoryImpl facultyRepository;

    public FacultyServiceImpl(FacultyRepositoryImpl facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    // Get
    public List<Faculty> getFaculties(String color) {
        List<Faculty> filterFaculties = new ArrayList<>();

        for (Faculty faculty: facultyRepository.getFaculties().values()) {
            if(faculty.getColor().equals(color)) {
                filterFaculties.add(faculty);
            }
        }

        return filterFaculties;
    }

    // Post
    public void addFaculty(Faculty faculty) {
        facultyRepository.addFaculty(faculty);
    }

    // Put
    public void updateFaculty(Faculty faculty) {
        facultyRepository.updateFaculty(faculty);
    }

    // Delete
    public void deleteFaculty(Long id) {
        facultyRepository.deleteFaculty(id);
    }
}
