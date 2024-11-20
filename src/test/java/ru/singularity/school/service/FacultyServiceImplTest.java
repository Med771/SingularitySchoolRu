package ru.singularity.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.singularity.school.model.Faculty;
import ru.singularity.school.repository.FacultyRepositoryImpl;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {

    @Mock
    private FacultyRepositoryImpl facultyRepository;

    @InjectMocks
    private FacultyServiceImpl facultyService;

    // Тест метода getFaculties(String color)
    @Test
    void shouldReturnFacultiesFilteredByColor() {
        // Arrange
        Faculty faculty1 = new Faculty(1L, "Engineering", "Blue");
        Faculty faculty2 = new Faculty(2L, "Science", "Green");
        HashMap<Long, Faculty> faculties = new HashMap<>();
        faculties.put(1L, faculty1);
        faculties.put(2L, faculty2);

        when(facultyRepository.getFaculties()).thenReturn(faculties);

        // Act
        List<Faculty> result = facultyService.getFaculties("Blue");

        // Assert
        assertEquals(1, result.size(), "Filtered list should contain only one faculty.");
        assertTrue(result.contains(faculty1), "Filtered list should contain the faculty with matching color.");

        verify(facultyRepository, times(1)).getFaculties();
    }

    // Тест метода addFaculty(Faculty faculty)
    @Test
    void shouldAddFacultyToRepository() {
        // Arrange
        Faculty faculty = new Faculty(1L, "Engineering", "Blue");

        // Act
        facultyService.addFaculty(faculty);

        // Assert
        verify(facultyRepository, times(1)).addFaculty(faculty);
    }

    // Тест метода updateFaculty(Faculty faculty)
    @Test
    void shouldUpdateFacultyInRepository() {
        // Arrange
        Faculty faculty = new Faculty(1L, "Engineering", "Blue");

        // Act
        facultyService.updateFaculty(faculty);

        // Assert
        verify(facultyRepository, times(1)).updateFaculty(faculty);
    }

    // Тест метода deleteFaculty(Long id)
    @Test
    void shouldDeleteFacultyById() {
        // Arrange
        Long id = 1L;

        // Act
        facultyService.deleteFaculty(id);

        // Assert
        verify(facultyRepository, times(1)).deleteFaculty(id);
    }

    // Тест метода getFaculties(String color) при отсутствии совпадений
    @Test
    void shouldReturnEmptyListWhenNoFacultyMatchesColor() {
        // Arrange
        when(facultyRepository.getFaculties()).thenReturn(new HashMap<>());

        // Act
        List<Faculty> result = facultyService.getFaculties("Red");

        // Assert
        assertTrue(result.isEmpty(), "Filtered list should be empty when no faculties match the color.");
        verify(facultyRepository, times(1)).getFaculties();
    }
}
