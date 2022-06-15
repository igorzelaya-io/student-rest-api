package com.example.repository;

import com.example.model.Student;
import com.example.model.status.ModelStatus;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Student student;

    private final String STUDENT_NAME = "Igor Zelaya";

    @Test
    public void injectedComponentsAreNotNull() {
        Assertions.assertNotNull(studentRepository);
    }

    @Test
    @Ignore
    public void shouldFetchStudentByName(){
        initRecords();
        Optional<Student> retrievedStudent = studentRepository
                .findByNameAndStatusContaining(STUDENT_NAME, 0);
        Assertions.assertTrue(retrievedStudent.isPresent());
        Assertions.assertEquals(retrievedStudent.get(), student);
    }

    private void initRecords() {
        student = Student
                .builder()
                .studentId(UUID.randomUUID().toString())
                .studentName(STUDENT_NAME)
                .studentEmail("i@z.com")
                .studentAge(18)
                .studentStatus(ModelStatus.ACTIVE)
                .build();
        testEntityManager.persist(student);
    }

}
