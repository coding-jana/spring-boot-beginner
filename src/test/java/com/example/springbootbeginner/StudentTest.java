package com.example.springbootbeginner;

import com.example.springbootbeginner.student.Student;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StudentTest {

    @Test
    public void testStudentConstructorWithId(){
        Long id = 1L;
        String name = "John";
        String email = "john@gmail.com";
        LocalDate dob = LocalDate.of(2000, Month.JANUARY,1);

        Student student = new Student(id, name, email, dob);

        assertNotNull(student);
        assertEquals(id, student.getId());
        assertEquals(name, student.getName());
        assertEquals(email, student.getEmail());
        assertEquals(dob, student.getDob());
    }

    @Test
    public void testStudentConstructorWithoutId(){
        String name = "Jane";
        String email = "jane@gmail.com";
        LocalDate dob = LocalDate.of(2000, Month.JANUARY,1);

        Student student = new Student(name, email, dob);

        assertNotNull(student);
        assertEquals(name, student.getName());
        assertEquals(email, student.getEmail());
        assertEquals(dob, student.getDob());
    }

    @Test
    public void testGetAge(){
        LocalDate dob = LocalDate.of(2000, Month.JANUARY, 2);
        Student student = new Student("Test", "test@example.com", dob);
        int age = student.getAge();
        assertEquals(24, age);
    }
    @Test
    public void testSetAndGetAge(){
        Long id = 100L;
        Student student = new Student();

        student.setId(id);

        assertEquals(id, student.getId());
    }

}
