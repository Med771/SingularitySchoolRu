package ru.singularity.school.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import ru.singularity.school.dto.NewFaculty;
import ru.singularity.school.model.Faculty;
import ru.singularity.school.model.Student;
import ru.singularity.school.service.FacultyServiceImpl;

import java.util.List;

@RestController
@RequestMapping(path = "/faculty")
public class FacultyController {
    private final FacultyServiceImpl facultyService;

    public FacultyController(FacultyServiceImpl facultyService) {
        this.facultyService = facultyService;
    }

    // Get
    @GetMapping(path="getStudentsById/{id}")
    public ResponseEntity<List<Student>> getStudents(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getStudents(id));
    }

    @GetMapping(path = "/findByFacultiesName/{name}")
    public ResponseEntity<List<Faculty>> getFacultyByFacultyName(@PathVariable String name) {
        return ResponseEntity.ok(facultyService.findByFacultyName(name));
    }

    @GetMapping(path = "findByFacultiesColor/{color}")
    public ResponseEntity<List<Faculty>> getFacultyByFacultyColor(@PathVariable String color) {
        return ResponseEntity.ok(facultyService.findByFacultyColor(color));
    }

    // Post
    @PostMapping(path = "/post")
    public ResponseEntity<Faculty> postFaculty(@RequestBody NewFaculty faculty) {
        return ResponseEntity.ok(facultyService.addFaculty(faculty));
    }

    // Put
    @PutMapping(path = "/put")
    public ResponseEntity<Faculty> putFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.updateFaculty(faculty));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.deleteFaculty(id));
    }
}
