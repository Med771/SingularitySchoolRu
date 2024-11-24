package ru.singularity.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.singularity.school.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColor(String color);
    List<Faculty> findByNameIgnoreCase(String name);
    List<Faculty> findByColorIgnoreCase(String color);
}
