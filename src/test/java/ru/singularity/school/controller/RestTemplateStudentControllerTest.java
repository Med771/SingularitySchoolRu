package ru.singularity.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.singularity.school.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestTemplateStudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void testGetFacultyByAge() {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/get/18", List.class)).isNotNull();
    }

    @Test
    void testGetFacultyByAgeBetween() {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/findByAgeBetween/0/18", List.class)).isNotNull();
    }

    @Test
    void postFaculty() {
        String facultyName = "Name";
        int age = 18;

        Student student = new Student();

        student.setName(facultyName);
        student.setAge(age);

        ResponseEntity<Student> response = restTemplate.exchange(
                "http://localhost:" + port + "/student/post",
                HttpMethod.POST,
                new HttpEntity<>(student),
                Student.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Student responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(facultyName, responseBody.getName());
        assertEquals(age, responseBody.getAge());
    }

    @Test
    void putFaculty() {
        String facultyName = "Name";
        int age = 18;

        Student student = new Student();

        student.setName(facultyName);
        student.setAge(age);

        ResponseEntity<Student> response = restTemplate.exchange(
                "http://localhost:" + port + "/student/put",
                HttpMethod.PUT,
                new HttpEntity<>(student),
                Student.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Student responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(facultyName, responseBody.getName());
        assertEquals(age, responseBody.getAge());
    }

    @Test
    void deleteFaculty() {
        String name = "name";
        int age = 20;

        Student student = new Student();

        student.setName(name);
        student.setAge(age);

        ResponseEntity<Student> responsePost = restTemplate.exchange(
                "http://localhost:" + port + "/student/post",
                HttpMethod.POST,
                new HttpEntity<>(student),
                Student.class
        );

        assertEquals(HttpStatus.OK, responsePost.getStatusCode());
        assertNotNull(responsePost.getBody());

        Student responseStudent = responsePost.getBody();
        Long responseId = responseStudent.getId();

        ResponseEntity<Student> responseDelete = restTemplate.exchange(
                "http://localhost:" + port + "/student/delete/" + responseId,
                HttpMethod.DELETE,
                null,
                Student.class
        );

        assertEquals(HttpStatus.OK, responseDelete.getStatusCode());

        Student responseBody = responseDelete.getBody();

        assertNotNull(responseBody);
        assertEquals(responseId, responseBody.getId());
        assertEquals(name, responseBody.getName());
        assertEquals(age, responseBody.getAge());
    }
}