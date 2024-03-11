package com.example.springbootbeginner;

import com.example.springbootbeginner.student.Student;
import com.example.springbootbeginner.student.StudentRepository;
import com.example.springbootbeginner.student.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StudentServiceTest {
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        studentService = new StudentService(studentRepository);
    }

    @Test
    public void testGetStudents() {
        // Arrange
        when(studentRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        Iterable<Student> students = studentService.getStudents();

        // Assert
        assertNotNull(students);
        assertEquals(0, students.spliterator().getExactSizeIfKnown());
    }

    @Test
    public void testAddNewStudent_Success() {
        // Arrange
        Student student = new Student("John", "john@example.com", null);

        // Mock behavior of studentRepository
        when(studentRepository.findStudentByEmail(any(String.class))).thenReturn(Optional.empty());
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // Act
        assertDoesNotThrow(() -> studentService.addNewStudent(student));

        // Assert
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void testAddNewStudent_DuplicateEmail() {
        // Arrange
        Student student = new Student("John", "john@example.com", null);

        // Mock behavior of studentRepository
        when(studentRepository.findStudentByEmail(any(String.class))).thenReturn(Optional.of(student));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> studentService.addNewStudent(student));
    }

    @Test
    public void testDeleteStudent_Exists() {
        // Arrange
        Long studentId = 1L;

        // Mock behavior of studentRepository
        when(studentRepository.existsById(studentId)).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> studentService.deleteStudent(studentId));
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    public void testDeleteStudent_NotExists() {
        // Arrange
        Long studentId = 1L;

        // Mock behavior of studentRepository
        when(studentRepository.existsById(studentId)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> studentService.deleteStudent(studentId));
    }

    @Test
    public void testUpdateStudent_Success() {
        // Arrange
        Long studentId = 1L;
        Student student = new Student(studentId, "John", "john@example.com", null);

        // Mock behavior of studentRepository
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentRepository.findStudentByEmail(any(String.class))).thenReturn(Optional.empty());

        // Act
        assertDoesNotThrow(() -> studentService.updateStudent(studentId, "Updated Name", "updated@example.com"));

        // Assert
        assertEquals("Updated Name", student.getName());
        assertEquals("updated@example.com", student.getEmail());
    }

    @Test
    public void testUpdateStudent_EmailTaken() {
        // Arrange
        Long studentId = 1L;
        Student student = new Student(studentId, "John", "john@example.com", null);

        // Mock behavior of studentRepository
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentRepository.findStudentByEmail(any(String.class))).thenReturn(Optional.of(student));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> studentService.updateStudent(studentId, "Updated Name", "updated@example.com"));
    }
}
