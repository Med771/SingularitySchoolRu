package ru.singularity.school.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import ru.singularity.school.model.Faculty;
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
    @GetMapping(path = "/get/{color}")
    public ResponseEntity<List<Faculty>> getFaculty(@PathVariable String color) {
        return ResponseEntity.ok(facultyService.getFaculties(color));
    }

    // Post
    @PostMapping(path = "/post")
    public ResponseEntity<Faculty> postFaculty(@RequestBody Faculty faculty) {
        facultyService.addFaculty(faculty);

        return ResponseEntity.ok(faculty);
    }

    // Put
    @PutMapping(path = "/put")
    public ResponseEntity<Faculty> putFaculty(@RequestBody Faculty faculty) {
        facultyService.updateFaculty(faculty);

        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);

        return ResponseEntity.ok("Successfully deleted");
    }
}