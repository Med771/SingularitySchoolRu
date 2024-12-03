package ru.singularity.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.singularity.school.model.Student;
import ru.singularity.school.repository.StudentRepository;
import ru.singularity.school.service.StudentServiceImpl;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentController.class)
public class WebMvcStudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentServiceImpl studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void getStudentsByAgeTests() throws Exception {
        Student student = new Student();
        Student student2 = new Student();

        student.setId(1L);
        student.setName("name");
        student.setAge(20);

        student2.setId(2L);
        student2.setName("name");
        student2.setAge(20);

        List<Student> students = List.of(
                student,
                student2
        );
        when(studentRepository.findByAge(20)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get/20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("name"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("name"));

        verify(studentService, times(1)).getStudents(20);
    }

    @Test
    public void getStudentsByAgeBetweenTest() throws Exception {
        Student student = new Student();
        Student student2 = new Student();

        student.setId(1L);
        student.setName("name");
        student.setAge(20);

        student2.setId(2L);
        student2.setName("name");
        student2.setAge(20);

        List<Student> students = List.of(
                student,
                student2
        );

        when(studentRepository.findByAgeBetween(10, 30)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/findByAgeBetween/10/30")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("name"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("name"));
    }

    @Test
    public void postStudentTest() throws Exception {
        String name = "name";
        int age = 20;

        Student student = new Student();

        student.setName(name);
        student.setAge(age);

        JSONObject user = new JSONObject();

        user.put("name", name);
        user.put("age", age);

        when(studentRepository.save(student)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student/post")
                        .content(user.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void putStudentTest() throws Exception {
        Long id = 1L;
        String name = "name";
        int age = 20;

        Student student = new Student();

        student.setId(1L);
        student.setName("name");
        student.setAge(20);

        JSONObject user = new JSONObject();

        user.put("id", id);
        user.put("name", name);
        user.put("age", age);

        when(studentRepository.save(student)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student/post")
                        .content(user.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        String name = "name";
        int age = 20;

        Student student = new Student();

        student.setName(name);
        student.setAge(age);

        JSONObject user = new JSONObject();

        user.put("name", name);
        user.put("age", age);

        when(studentRepository.save(student)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/put")
                        .content(user.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        Long id = 1L;
        String name = "name";
        int age = 20;

        Student student = new Student();

        student.setName(name);
        student.setAge(age);

        when(studentService.delete(id)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/delete/" + id)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }
}
