package ru.singularity.school.service;

import org.springframework.stereotype.Service;
import ru.singularity.school.model.Student;
import ru.singularity.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

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

    public List<Student> findStudentsByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    // Post
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    // Put
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    // Delete
    public Student delete(Long id) {
        Optional<Student> student = studentRepository.findById(id);

        if (student.isPresent()) {
            Long facultyId = student.get().getId();
            String facultyName = student.get().getName();
            int facultyAge = student.get().getAge();

            studentRepository.deleteById(facultyId);

            Student studentResponse = new Student();

            studentResponse.setId(facultyId);
            studentResponse.setName(facultyName);
            studentResponse.setAge(facultyAge);

            return studentResponse;
        }

        return null;
    }
}
