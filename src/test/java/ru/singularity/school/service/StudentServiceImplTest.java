package ru.singularity.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.singularity.school.model.Student;
import ru.singularity.school.repository.StudentRepositoryImpl;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepositoryImpl studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    // Тест метода getStudents(int age)
    @Test
    void shouldReturnStudentsFilteredByAge() {
        // Arrange
        Student student1 = new Student(1L, "Alice", 20);
        Student student2 = new Student(2L, "Bob", 22);
        Student student3 = new Student(3L, "Charlie", 20);

        HashMap<Long, Student> students = new HashMap<>();
        students.put(1L, student1);
        students.put(2L, student2);
        students.put(3L, student3);

        when(studentRepository.getStudents()).thenReturn(students);

        // Act
        List<Student> result = studentService.getStudents(20);

        // Assert
        assertEquals(2, result.size(), "Filtered list should contain two students with age 20.");
        assertTrue(result.contains(student1), "Filtered list should contain student1.");
        assertTrue(result.contains(student3), "Filtered list should contain student3.");
        verify(studentRepository, times(1)).getStudents();
    }

    // Тест метода addStudent(Student student)
    @Test
    void shouldAddStudentToRepository() {
        // Arrange
        Student student = new Student(1L, "Alice", 20);

        // Act
        studentService.addStudent(student);

        // Assert
        verify(studentRepository, times(1)).addStudent(student);
    }

    // Тест метода updateStudent(Student student)
    @Test
    void shouldUpdateStudentInRepository() {
        // Arrange
        Student student = new Student(1L, "Alice", 21);

        // Act
        studentService.updateStudent(student);

        // Assert
        verify(studentRepository, times(1)).updateStudent(student);
    }

    // Тест метода delete(Long id)
    @Test
    void shouldDeleteStudentById() {
        // Arrange
        Long id = 1L;

        // Act
        studentService.delete(id);

        // Assert
        verify(studentRepository, times(1)).deleteStudent(id);
    }

    // Тест метода getStudents(int age) при отсутствии совпадений
    @Test
    void shouldReturnEmptyListWhenNoStudentMatchesAge() {
        // Arrange
        when(studentRepository.getStudents()).thenReturn(new HashMap<>());

        // Act
        List<Student> result = studentService.getStudents(25);

        // Assert
        assertTrue(result.isEmpty(), "Filtered list should be empty when no students match the age.");
        verify(studentRepository, times(1)).getStudents();
    }
}
