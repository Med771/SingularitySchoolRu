package ru.singularity.school.service;

import org.springframework.stereotype.Service;
import ru.singularity.school.dto.NewStudent;
import ru.singularity.school.model.Faculty;
import ru.singularity.school.model.Student;
import ru.singularity.school.repository.FacultyRepository;
import ru.singularity.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public final class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(StudentRepository studentRepository,
                              FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    // Get
    public List<Student> getStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Integer getStudentsCount() {
        return studentRepository.countExpenses();
    }

    public Double getStudentsAverage() {
        return studentRepository.averageAge();

    }
    public List<Student> findStudentsByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public List<Student> getFiveLastStudents() {
        return studentRepository.findLastFiveStudents();
    }

    // Post
    public Student addStudent(NewStudent newStudent) {
        Student student = new Student();

        student.setName(newStudent.getName());
        student.setAge(newStudent.getAge());

        return studentRepository.save(student);
    }

    public boolean addFaculty(Long facultyId, Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Faculty> faculty = facultyRepository.findById(facultyId);

        if (student.isEmpty() || faculty.isEmpty()) {
            return false;
        }

        faculty.get().addStudent(student.get());

        studentRepository.save(student.get());
        facultyRepository.save(faculty.get());

        return true;
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
