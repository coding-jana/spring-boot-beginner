package com.example.springbootbeginner;

import com.example.springbootbeginner.student.Student;
import com.example.springbootbeginner.student.StudentController;
import com.example.springbootbeginner.student.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudentControllerTest {
    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        StudentController studentController = new StudentController(studentService);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void testGetStudents() throws Exception {
        mockMvc.perform(get("/api/v1/student/"))
                .andExpect(status().isOk());
        verify(studentService).getStudents();
    }

    @Test
    public void testRegisterNewStudent() throws Exception {
        mockMvc.perform(post("/api/v1/student/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\", \"email\":\"john@example.com\", \"dob\":\"2000-01-01\"}"))
                .andExpect(status().isOk());
        verify(studentService).addNewStudent(any(Student.class));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Long studentId = 1L;
        mockMvc.perform(delete("/api/v1/student/{studentId}", studentId))
                .andExpect(status().isOk());
        verify(studentService).deleteStudent(studentId);
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Long studentId = 1L;
        String updatedName = "Updated Name";
        String updatedEmail = "updated@example.com";

        Student updatedStudent = new Student();
        updatedStudent.setName(updatedName);
        updatedStudent.setEmail(updatedEmail);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(updatedStudent);

        mockMvc.perform(put("/api/v1/student/{studentId}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        verify(studentService).updateStudent(studentId, updatedName, updatedEmail);
    }

}