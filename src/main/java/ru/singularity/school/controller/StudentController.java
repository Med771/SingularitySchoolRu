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
    public ResponseEntity<List<Student>> getStudent(@PathVariable int age) {
        return ResponseEntity.ok(studentService.getStudents(age));
    }

    @GetMapping(path = "/findByAgeBetween/{minAge}/{maxAge}")
    public ResponseEntity<List<Student>> getStudentsByAgeBetween(@PathVariable int minAge,
                                                                @PathVariable int maxAge) {
        return ResponseEntity.ok(studentService.findStudentsByAgeBetween(minAge, maxAge));
    }

    // Post
    @PostMapping(path = "/post")
    public ResponseEntity<Student> postFaculty(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    // Put
    @PutMapping(path = "/put")
    public ResponseEntity<Student> putFaculty(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(student));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Student> deleteFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.delete(id));
    }
}
