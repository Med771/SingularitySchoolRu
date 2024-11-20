package ru.singularity.school.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.singularity.school.model.Student;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class StudentRepositoryImplTest {

    private StudentRepositoryImpl studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository = new StudentRepositoryImpl();
    }

    // Тест метода getStudents()
    @Test
    void shouldReturnEmptyMapInitially() {
        // Act
        HashMap<Long, Student> students = studentRepository.getStudents();

        // Assert
        assertNotNull(students, "Students map should not be null.");
        assertTrue(students.isEmpty(), "Students map should be empty initially.");
    }

    // Тест метода addStudent(Student student)
    @Test
    void shouldAddStudentToRepository() {
        // Arrange
        Student student = new Student(1L, "Alice", 20);

        // Act
        studentRepository.addStudent(student);

        // Assert
        HashMap<Long, Student> students = studentRepository.getStudents();
        assertEquals(1, students.size(), "Students map should contain one entry.");
        assertTrue(students.containsValue(student), "The added student should exist in the repository.");
    }

    // Тест метода updateStudent(Student student)
    @Test
    void shouldUpdateExistingStudent() {
        // Arrange
        Student oldStudent = new Student(1L, "Alice", 20);
        Student updatedStudent = new Student(1L, "Alice", 21);

        studentRepository.addStudent(oldStudent);

        // Act
        studentRepository.updateStudent(updatedStudent);

        // Assert
        HashMap<Long, Student> students = studentRepository.getStudents();
        assertEquals(1, students.size(), "Students map should contain one entry.");
        assertTrue(students.containsValue(updatedStudent), "The updated student should replace the old one.");
    }

    // Тест метода updateStudent(Student student) с добавлением нового студента
    @Test
    void shouldAddStudentWhenIdDoesNotExistInUpdate() {
        // Arrange
        Student student = new Student(2L, "Bob", 22);

        // Act
        studentRepository.updateStudent(student);

        // Assert
        HashMap<Long, Student> students = studentRepository.getStudents();
        assertEquals(1, students.size(), "Students map should contain one entry.");
        assertTrue(students.containsValue(student), "The student should be added if the ID does not exist.");
    }

    // Тест метода deleteStudent(Long id)
    @Test
    void shouldDeleteStudentById() {
        // Arrange
        Student student = new Student(1L, "Alice", 20);
        studentRepository.addStudent(student);

        // Act
        studentRepository.deleteStudent(1L);

        // Assert
        HashMap<Long, Student> students = studentRepository.getStudents();
        assertTrue(students.isEmpty(), "Students map should be empty after deletion.");
    }

    // Тест метода deleteStudent(Long id) при удалении несуществующего студента
    @Test
    void shouldNotThrowErrorWhenDeletingNonExistentStudent() {
        // Act & Assert
        assertDoesNotThrow(() -> studentRepository.deleteStudent(99L),
                "Deleting a non-existent student should not throw an exception.");
    }
}
