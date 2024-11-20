package ru.singularity.school.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.singularity.school.model.Faculty;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class FacultyRepositoryImplTest {

    private FacultyRepositoryImpl facultyRepository;

    @BeforeEach
    void setUp() {
        facultyRepository = new FacultyRepositoryImpl();
    }

    // Тест метода getFaculties()
    @Test
    void shouldReturnEmptyMapInitially() {
        // Act
        HashMap<Long, Faculty> faculties = facultyRepository.getFaculties();

        // Assert
        assertNotNull(faculties, "Faculties map should not be null.");
        assertTrue(faculties.isEmpty(), "Faculties map should be empty initially.");
    }

    // Тест метода addFaculty(Faculty faculty)
    @Test
    void shouldAddFacultyToRepository() {
        // Arrange
        Faculty faculty = new Faculty(1L, "Engineering", "Blue");

        // Act
        facultyRepository.addFaculty(faculty);

        // Assert
        HashMap<Long, Faculty> faculties = facultyRepository.getFaculties();
        assertEquals(1, faculties.size(), "Faculties map should contain one entry.");
        assertTrue(faculties.containsValue(faculty), "The added faculty should exist in the repository.");
    }

    // Тест метода updateFaculty(Faculty faculty)
    @Test
    void shouldUpdateExistingFaculty() {
        // Arrange
        Faculty oldFaculty = new Faculty(1L, "Engineering", "Blue");
        Faculty updatedFaculty = new Faculty(1L, "Science", "Green");

        facultyRepository.addFaculty(oldFaculty);

        // Act
        facultyRepository.updateFaculty(updatedFaculty);

        // Assert
        HashMap<Long, Faculty> faculties = facultyRepository.getFaculties();
        assertEquals(1, faculties.size(), "Faculties map should contain one entry.");
        assertTrue(faculties.containsValue(updatedFaculty), "The updated faculty should replace the old one.");
    }

    // Тест метода updateFaculty(Faculty faculty) с добавлением нового факультета
    @Test
    void shouldAddFacultyWhenIdDoesNotExistInUpdate() {
        // Arrange
        Faculty faculty = new Faculty(2L, "Mathematics", "Yellow");

        // Act
        facultyRepository.updateFaculty(faculty);

        // Assert
        HashMap<Long, Faculty> faculties = facultyRepository.getFaculties();
        assertEquals(1, faculties.size(), "Faculties map should contain one entry.");
        assertTrue(faculties.containsValue(faculty), "The faculty should be added if the ID does not exist.");
    }

    // Тест метода deleteFaculty(Long id)
    @Test
    void shouldDeleteFacultyById() {
        // Arrange
        Faculty faculty = new Faculty(1L, "Engineering", "Blue");
        facultyRepository.addFaculty(faculty);

        // Act
        facultyRepository.deleteFaculty(1L);

        // Assert
        HashMap<Long, Faculty> faculties = facultyRepository.getFaculties();
        assertTrue(faculties.isEmpty(), "Faculties map should be empty after deletion.");
    }

    // Тест метода deleteFaculty(Long id) при удалении несуществующего факультета
    @Test
    void shouldNotThrowErrorWhenDeletingNonExistentFaculty() {
        // Act & Assert
        assertDoesNotThrow(() -> facultyRepository.deleteFaculty(99L),
                "Deleting a non-existent faculty should not throw an exception.");
    }
}
