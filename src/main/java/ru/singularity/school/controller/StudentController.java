package ru.singularity.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.singularity.school.model.Student;
import ru.singularity.school.service.StudentServiceImpl;

import java.util.List;

@RestController
@RequestMapping(path = "/student")
public class StudentController {
    private final StudentServiceImpl studentService;

    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    // Get
    @GetMapping(path = "/get/{age}")
    public ResponseEntity<List<Student>> getFaculty(@PathVariable int age) {
        return ResponseEntity.ok(studentService.getStudents(age));
    }

    // Post
    @PostMapping(path = "/post")
    public ResponseEntity<Student> postFaculty(@RequestBody Student student) {
        studentService.addStudent(student);

        return ResponseEntity.ok(student);
    }

    // Put
    @PutMapping(path = "/put")
    public ResponseEntity<Student> putFaculty(@RequestBody Student student) {
        studentService.updateStudent(student);

        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFaculty(@PathVariable Long id) {
        studentService.delete(id);

        return ResponseEntity.ok("Successfully deleted");
    }
}
