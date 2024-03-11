package com.example.springbootbeginner;

import com.example.springbootbeginner.student.Student;
import com.example.springbootbeginner.student.StudentConfig;
import com.example.springbootbeginner.student.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;

public class StudentConfigTest {

    @Test
    public void testCommandLineRunner() throws Exception {
        StudentRepository mockRepository = Mockito.mock(StudentRepository.class);
        StudentConfig studentConfig = new StudentConfig();
        CommandLineRunner commandLineRunner = studentConfig.commandLineRunner(mockRepository);

        commandLineRunner.run((String) null);

        verify(mockRepository).saveAll(Mockito.anyList());
    }
}
