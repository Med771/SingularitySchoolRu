package ru.singularity.school.service;

import org.springframework.stereotype.Service;
import ru.singularity.school.model.Faculty;
import ru.singularity.school.repository.FacultyRepository;

import java.util.List;

@Service
public final class FacultyServiceImpl implements FacultyService {
    // Init
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    // Get
    public List<Faculty> getFaculties(String color) {
        return facultyRepository.findByColor(color);
    }

    // Post
    public void addFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
    }

    // Put
    public void updateFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
    }

    // Delete
    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
}
