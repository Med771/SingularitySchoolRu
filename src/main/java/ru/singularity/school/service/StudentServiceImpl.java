package ru.singularity.school.service;

import org.springframework.stereotype.Service;
import ru.singularity.school.model.Faculty;
import ru.singularity.school.model.Student;
import ru.singularity.school.repository.StudentRepository;

import java.util.List;

@Service
public final class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Get
    public List<Student> getStudents(int age) {
        return studentRepository.findByAge(age);
    }

    public Faculty getFaculty(Long id) {
        return studentRepository.findById(id).get().getFaculty();
    }

    public List<Student> findStudentsByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    // Post
    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    // Put
    public void updateStudent(Student student) {
        studentRepository.save(student);
    }

    // Delete
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }
}
