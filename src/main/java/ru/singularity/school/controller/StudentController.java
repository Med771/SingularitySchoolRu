package ru.singularity.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.singularity.school.dto.NewStudent;
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
        return ResponseEntity.ok(studentService.getStudentsByAge(age));
    }

    @GetMapping(path = "/findByAgeBetween/{minAge}/{maxAge}")
    public ResponseEntity<List<Student>> getStudentsByAgeBetween(@PathVariable int minAge,
                                                                @PathVariable int maxAge) {
        return ResponseEntity.ok(studentService.findStudentsByAgeBetween(minAge, maxAge));
    }

    @GetMapping(path = "/countStudents")
    public ResponseEntity<Integer> countStudents() {
        return ResponseEntity.ok(studentService.getStudentsCount());
    }

    @GetMapping(path = "/averageStudents")
    public ResponseEntity<Double> calculateAverage() {
        return ResponseEntity.ok(studentService.getStudentsAverage());
    }

    @GetMapping(path = "/fiveLastStudents")
    public ResponseEntity<List<Student>> getFiveLastStudents() {
        return ResponseEntity.ok(studentService.getFiveLastStudents());
    }

    @GetMapping(path = "/getNamesStartingWithSymbol")
    public ResponseEntity<List<String>> getNamesStartingWithSymbol(@RequestParam(value = "symbol", defaultValue = "a") String symbol) {
        return ResponseEntity.ok(studentService.getNamesStartingWithSymbol(symbol));
    }

    @GetMapping(path = "/averageStudentsByStream")
    public ResponseEntity<Double> getAverageStudentsByStream() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }

    @GetMapping(path="/print-parallel")
    public ResponseEntity<HttpStatus> printParallel() {
        studentService.printParallel();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/print-synchronized")
    public ResponseEntity<HttpStatus> printSynchronized() {
        studentService.printSynchronized();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Post
    @PostMapping(path = "/post")
    public ResponseEntity<Student> postFaculty(@RequestBody NewStudent student) {
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    @PostMapping(path="set/faculty/{faculty_id}/{student_id}")
    public ResponseEntity<HttpStatus> setFaculty(@PathVariable Long faculty_id,
                                                 @PathVariable Long student_id) {
        if (studentService.addFaculty(faculty_id, student_id)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
