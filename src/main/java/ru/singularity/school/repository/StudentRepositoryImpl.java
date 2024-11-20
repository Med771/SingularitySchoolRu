package ru.singularity.school.repository;

import org.springframework.stereotype.Repository;
import ru.singularity.school.model.Student;

import java.util.HashMap;

// API data
@Repository
public final class StudentRepositoryImpl implements StudentRepository{
    // Init
    private final HashMap<Long, Student> students;
    private Long keyId;

    // Constructor
    public StudentRepositoryImpl() {
        students = new HashMap<>();
        keyId = 0L;
    }

    // Get
    public HashMap<Long, Student> getStudents() {
        return students;
    }

    // Post
    public void addStudent(Student student) {
        students.put(student.getId(), student);
        keyId++;
    }

    // Put
    public void updateStudent(Student student) {
        for (Long key : students.keySet()) {
            if (students.get(key).getId().equals(student.getId())) {
                students.put(key, student);
                return;
            }
        }

        students.put(keyId, student);
        keyId++;
    }

    // Delete
    public void deleteStudent(Long id) {
        if (students.get(id) != null) {
            students.remove(id);
        }
    }
}
