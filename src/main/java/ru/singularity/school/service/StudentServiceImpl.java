package ru.singularity.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Object isStudentLock = new Object();

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository,
                              FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    // Get
    public List<Student> getStudentsByAge(int age) {
        logger.info("Get students by age");
        return studentRepository.findByAge(age);
    }

    public Integer getStudentsCount() {
        logger.info("Get students count");
        return studentRepository.countExpenses();
    }

    public Double getStudentsAverage() {
        logger.info("Get students average");
        return studentRepository.averageAge();

    }
    public List<Student> findStudentsByAgeBetween(int minAge, int maxAge) {
        logger.info("Find students by age between");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public List<Student> getFiveLastStudents() {
        logger.info("Get five last students");
        return studentRepository.findLastFiveStudents();
    }

    public List<String> getNamesStartingWithSymbol(String symbol) {
        logger.info("Get names starting with symbol");

        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .filter(name -> name.toLowerCase().startsWith(symbol.toLowerCase()))
                .sorted()
                .map(String::toUpperCase)
                .toList();
    }

    public double getAverageAge() {
        logger.info("Get average age");

        return studentRepository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    public void printParallel() {
        // Для показа работоспособности без бд реализовон свой список
        String[] names = new String[]{"name_1", "name_2", "name_3", "name_4", "name_5", "name_6"};

        /*List<String> names = studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .toList();*/
        printForParallel(names[0]);

        new Thread(() -> {
            printForParallel(names[2]);
            printForParallel(names[3]);
        }).start();


        new Thread(() -> {
            printForParallel(names[4]);
            printForParallel(names[5]);
        }).start();

        printForParallel(names[1]);
    }

    public void printSynchronized() {
        // Для показа работоспособности без бд реализовон свой список
        String[] names = new String[]{"name_1", "name_2", "name_3", "name_4", "name_5", "name_6"};

        /*List<String> names = studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .toList();*/

        printForSynchronized(names[0]);

        new Thread(() -> {
            printForSynchronized(names[2]);
            printForSynchronized(names[3]);
        }).start();


        new Thread(() -> {
            printForSynchronized(names[4]);
            printForSynchronized(names[5]);
        }).start();

        printForSynchronized(names[1]);
    }

    private void printForParallel(String name) {
        System.out.println(name);
    }

    private void printForSynchronized(String name) {
        synchronized (isStudentLock) {
            System.out.println(name);
        }
    }

    // Post
    public Student addStudent(NewStudent newStudent) {
        logger.info("Add new student");

        Student student = new Student();

        student.setName(newStudent.getName());
        student.setAge(newStudent.getAge());

        return studentRepository.save(student);
    }

    public boolean addFaculty(Long facultyId, Long studentId) {
        logger.info("Add new faculty");

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
        logger.info("Update student");
        return studentRepository.save(student);
    }

    // Delete
    public Student delete(Long id) {
        logger.info("Delete student");
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

        return new Student();
    }
}
