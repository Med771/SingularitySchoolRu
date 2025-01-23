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
import ru.singularity.school.model.Faculty;
import ru.singularity.school.model.Student;
import ru.singularity.school.repository.FacultyRepository;
import ru.singularity.school.service.FacultyServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FacultyController.class)
public class WebMvcFacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void getFacultiesByColor() throws Exception {
        Long id_1 = 1L;
        Long id_2 = 2L;

        String name = "name";
        String color = "blue";

        Faculty faculty_1 = new Faculty();
        Faculty faculty_2 = new Faculty();

        faculty_1.setId(id_1);
        faculty_2.setId(id_2);
        faculty_1.setName(name);
        faculty_2.setName(color);
        faculty_1.setColor(color);
        faculty_2.setColor(color);

        List<Faculty> faculties = List.of(faculty_1, faculty_2);

        when(facultyRepository.findByColor(color)).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/getByColor/" + color)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentsTest() throws Exception {
        Long id_1 = 1L;
        Long id_2 = 2L;

        String name = "name";
        String color = "color";
        int age = 18;

        Student student_1 = new Student();
        student_1.setId(id_1);
        student_1.setName(name);
        student_1.setAge(age);

        Student student_2 = new Student();
        student_2.setId(id_2);
        student_2.setName(name);
        student_2.setAge(age);

        List<Student> students = List.of(student_1, student_2);

        Faculty faculty_1 = new Faculty();

        faculty_1.setId(id_1);
        faculty_1.setName(name);
        faculty_1.setColor(color);
        faculty_1.setStudents(students);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty_1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/getStudentsById/" + id_1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getFacultyByFacultyNameTest() throws Exception {
        String name = "name";
        String color = "color";

        Faculty faculty_1 = new Faculty();
        faculty_1.setId(1L);
        faculty_1.setName(name);
        faculty_1.setColor(color);

        Faculty faculty_2 = new Faculty();
        faculty_2.setId(2L);
        faculty_2.setName(name);
        faculty_2.setColor(color);

        List<Faculty> faculties = List.of(faculty_1, faculty_2);

        when(facultyRepository.findByNameIgnoreCase(any(String.class))).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/findByFacultiesName/" + name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getFacultyByFacultyColor() throws Exception {
        String name = "name";
        String color = "color";

        Faculty faculty_1 = new Faculty();
        faculty_1.setId(1L);
        faculty_1.setName(name);
        faculty_1.setColor(color);

        Faculty faculty_2 = new Faculty();
        faculty_2.setId(2L);
        faculty_2.setName(name);
        faculty_2.setColor(color);

        List<Faculty> faculties = List.of(faculty_1, faculty_2);

        when(facultyRepository.findByColorIgnoreCase(any(String.class))).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/findByFacultiesColor/" + name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void postFacultyTest() throws Exception {
        Long id = 1L;
        String name = "name";
        String color = "color";

        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName(name);
        faculty.setColor(color);

        JSONObject facultyObject = new JSONObject();

        facultyObject.put("name", name);
        facultyObject.put("color", color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty/post")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void putFacultyTest() throws Exception {
        Long id = 1L;
        String name = "name";
        String color = "color";

        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName(name);
        faculty.setColor(color);

        JSONObject facultyObject = new JSONObject();

        facultyObject.put("name", name);
        facultyObject.put("color", color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty/put")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        Long id = 1L;
        String name = "name";
        String color = "color";

        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyService.deleteFaculty(any(Long.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/delete/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }
}
