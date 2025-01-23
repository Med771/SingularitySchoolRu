package ru.singularity.school.service;

import org.springframework.stereotype.Service;
import ru.singularity.school.dto.NewFaculty;
import ru.singularity.school.model.Faculty;
import ru.singularity.school.model.Student;
import ru.singularity.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Student> getStudents(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isPresent()) {
            return faculty.get().getStudents();
        }

        return new ArrayList<>();
    }

    public List<Faculty> findByFacultyName(String facultyName) {
        return facultyRepository.findByNameIgnoreCase(facultyName);
    }

    public List<Faculty> findByFacultyColor(String facultyColor) {
        return facultyRepository.findByColorIgnoreCase(facultyColor);
    }

    // Post
    public Faculty addFaculty(NewFaculty newFaculty) {
        Faculty faculty = new Faculty();

        faculty.setName(newFaculty.getName());
        faculty.setColor(newFaculty.getColor());

        return facultyRepository.save(faculty);
    }

    // Put
    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    // Delete
    public Faculty deleteFaculty(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isPresent()) {
            Long facultyId = faculty.get().getId();
            String facultyName = faculty.get().getName();
            String facultyColor = faculty.get().getColor();

            facultyRepository.deleteById(facultyId);

            Faculty facultyResponse = new Faculty();

            facultyResponse.setId(facultyId);
            facultyResponse.setName(facultyName);
            facultyResponse.setColor(facultyColor);

            return facultyResponse;
        }

        return new Faculty();
    }
}
