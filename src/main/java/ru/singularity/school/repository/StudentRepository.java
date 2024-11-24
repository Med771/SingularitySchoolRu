package ru.singularity.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.singularity.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);
    List<Student> findByAgeBetween(int min, int max);
}