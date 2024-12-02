package ru.singularity.school.controller;

import org.assertj.core.api.Assertions;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.singularity.school.model.Faculty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestTemplateFacultyControllerTest {
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
    void getFacultiesByColor() {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/get/white", String.class)).isNotNull();
    }

    @Test
    void getStudents() {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/getStudents/3", String.class)).isNotNull();
    }

    @Test
    void getFacultyByFacultiesByColorName() {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/findByFacultyName/steve", String.class))
                .isNotNull();
    }

    @Test
    void getFacultyByFacultiesByColorColor() {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/findByFacultyColor/white", String.class))
                .isNotNull();
    }

    @Test
    void postFaculty() {
        String name = "Name";
        String color = "Color";

        Faculty faculty = new Faculty();

        faculty.setName(name);
        faculty.setColor(color);

        ResponseEntity<Faculty> response = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/post",
                HttpMethod.POST,
                new HttpEntity<>(faculty),
                Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Faculty responseFaculty = response.getBody();

        assertNotNull(responseFaculty);
        assertEquals(faculty.getName(), responseFaculty.getName());
        assertEquals(faculty.getColor(), responseFaculty.getColor());
    }

    @Test
    void putFaculty() {
        String name = "Name";
        String color = "Color";

        Faculty faculty = new Faculty();

        faculty.setName(name);
        faculty.setColor(color);

        ResponseEntity<Faculty> response = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/put",
                HttpMethod.PUT,
                new HttpEntity<>(faculty),
                Faculty.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Faculty responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(faculty.getName(), responseBody.getName());
        assertEquals(faculty.getColor(), responseBody.getColor());
    }

    @Test
    void deleteFaculty() {
        String name = "Name";
        String color = "Color";

        Faculty faculty = new Faculty();

        faculty.setName(name);
        faculty.setColor(color);

        ResponseEntity<Faculty> responsePost = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/post",
                HttpMethod.POST,
                new HttpEntity<>(faculty),
                Faculty.class);

        assertEquals(HttpStatus.OK, responsePost.getStatusCode());
        assertNotNull(responsePost.getBody());

        Faculty responseFaculty = responsePost.getBody();
        Long facultyId = responseFaculty.getId();

        ResponseEntity<Faculty> responseDelete = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/delete/" + facultyId,
                HttpMethod.DELETE,
                null,
                Faculty.class
        );

        assertEquals(HttpStatus.OK, responseDelete.getStatusCode());

        Faculty responseBody = responseDelete.getBody();

        assertNotNull(responseBody);
        assertEquals(facultyId, responseBody.getId());
        assertEquals(faculty.getName(), responseBody.getName());
        assertEquals(faculty.getColor(), responseBody.getColor());
    }
}