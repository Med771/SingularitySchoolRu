package ru.singularity.school.service;

import org.springframework.stereotype.Service;
import ru.singularity.school.model.Student;
import ru.singularity.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public final class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Get
    public List<Student> getStudents(int age) {
        List<Student> filterStudents = new ArrayList<>();

        for (Student student: studentRepository.getStudents().values()) {
            if (student.getAge() == age) {
                filterStudents.add(student);
            }
        }

        return filterStudents;
    }

    // Post
    public void addStudent(Student student) {
        studentRepository.addStudent(student);
    }

    // Put
    public void updateStudent(Student student) {
        studentRepository.updateStudent(student);
    }

    // Delete
    public void delete(Long id) {
        studentRepository.deleteStudent(id);
    }
}
