package com.example.service;

import com.example.config.WebMvcConfiguration;
import com.example.dto.StudentDto;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Student;
import com.example.model.mapper.StudentMapper;
import com.example.model.mapper.SubjectMapper;
import com.example.model.status.ModelStatus;
import com.example.repository.StudentRepository;
import com.example.repository.SubjectRepository;
import com.example.service.implementation.StudentServiceImpl;
import com.example.service.implementation.SubjectServiceImpl;
import com.example.utils.MessageKey;
import com.example.utils.Messages;
import com.example.utils.SortingPagingUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.fail;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@Import({Messages.class, WebMvcConfiguration.class})
public class StudentServiceTest {

    @Autowired
    private Messages messages;

    @Autowired
    private SortingPagingUtils sortingPagingUtils;

    private StudentRepository studentRepository;

    private StudentService studentService;

    private SubjectRepository subjectRepository;

    private SubjectService subjectService;

    private StudentDto studentDto;

    private Student student;

    private static final String STUDENT_ID = UUID.randomUUID().toString();

    private static final String STUDENT_NAME = "Igor Zelaya";

    private static final String STUDENT_EMAIL = "a@b.com";

    private String STUDENT_NOT_FOUND;

    @Before
    public void init() {

        studentRepository = Mockito.mock(StudentRepository.class);
        subjectRepository = Mockito.mock(SubjectRepository.class);
        studentService = new StudentServiceImpl(studentRepository, subjectRepository, messages, sortingPagingUtils);

        STUDENT_NOT_FOUND  = messages
                .getMessage(MessageKey.STUDENT_NOT_FOUND.getKey());

        student = Student
                .builder()
                .studentId(STUDENT_ID)
                .studentName(STUDENT_NAME)
                .studentEmail(STUDENT_EMAIL)
                .studentAge(20)
                .studentSubjects(new ArrayList<>())
                .studentStatus(ModelStatus.ACTIVE)
                .build();

        studentDto = StudentDto
                .builder()
                .studentId(STUDENT_ID)
                .studentName(STUDENT_NAME)
                .studentAge(20)
                .studentEmail("a@b.com")
                .studentSubjects(new ArrayList<>())
                .build();
    }

    @Test
    public void shouldReturnStudentById() {
        when(studentRepository
                .findByNameAndStatusContaining(STUDENT_ID, 0))
                .thenReturn(Optional.of(student));

        StudentDto studentDto = studentService.findStudentById(STUDENT_ID, 0);

        Assertions.assertThat(studentDto).isNotNull();
        Assertions.assertThat(studentDto.getStudentId()).isEqualTo(STUDENT_ID);
        Assertions.assertThat(studentDto.getStudentName()).isEqualTo(student.getStudentName());
        Assertions.assertThat(studentDto.getStudentAge()).isEqualTo(student.getStudentAge());
        Assertions.assertThat(studentDto.getStudentEmail()).isEqualTo(student.getStudentEmail());
    }

    @Test
    public void whenNotFoundById_throwException() {
        Map<String, String> params = Map.of("studentId", STUDENT_ID);

        final String EX_MESSAGE = new StringBuilder(STUDENT_NOT_FOUND)
                .append(" ")
                .append(params).toString();

        when(studentRepository.findById(STUDENT_ID))
                .thenReturn(Optional.empty());
        try {
            StudentDto studentDto = studentService.findStudentById(STUDENT_ID, 0);
            fail("Exception must be thrown");
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(ResourceNotFoundException.class);
            assertThat(ex.getMessage()).isEqualTo(EX_MESSAGE);
        }
    }

    @Test
    public void shouldReturnStudentByName() {
        when(studentRepository
                .findByNameAndStatusContaining(STUDENT_NAME, 0))
                .thenReturn(Optional.of(student));

        StudentDto studentDto = studentService.findStudentByName(STUDENT_NAME, 0);
        Assertions.assertThat(studentDto).isNotNull();
        Assertions.assertThat(studentDto.getStudentId()).isEqualTo(STUDENT_ID);
        Assertions.assertThat(studentDto.getStudentName()).isEqualTo(student.getStudentName());
        Assertions.assertThat(studentDto.getStudentAge()).isEqualTo(student.getStudentAge());
        Assertions.assertThat(studentDto.getStudentEmail()).isEqualTo(student.getStudentEmail());
    }

    @Test
    public void whenNotFoundByName_throwException() {
        Map<String, String> params = Map.of("studentName", STUDENT_NAME);

        final String EX_MESSAGE = new StringBuilder(STUDENT_NOT_FOUND)
                .append(" ")
                .append(params).toString();

        when(studentRepository.findByNameAndStatusContaining(STUDENT_NAME, 0))
                .thenReturn(Optional.empty());

        try {
            StudentDto studentDto = studentService.findStudentByName(STUDENT_NAME, 0);
            fail("Should throw exception.");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(ResourceNotFoundException.class);
            assertThat(e.getMessage()).isEqualTo(EX_MESSAGE);
        }
    }

    @Test
    public void getPaginatedStudents() {
        Page<Student> studentDtoFakePage = new PageImpl<>(List.of(student));
        List<Sort.Order> sortOrder = List.of(new Sort.Order(Sort.Direction.DESC, "studentId"));
        Pageable pageable = PageRequest.of(0, 3, Sort.by(sortOrder));

        when(studentRepository.findByStudentNameContaining(STUDENT_NAME, pageable))
                .thenReturn(studentDtoFakePage);

        Page<StudentDto> studentDtos = studentService
                .findPaginatedSortedStudents(STUDENT_NAME, 0, 3, new String[]{"studentId, desc"});

        assertThat(studentDtos).isNotNull();
        assertThat(studentDtos.getSize()).isEqualTo(studentDtoFakePage.getSize());
        assertThat(studentDtos.getNumber()).isEqualTo(studentDtoFakePage.getNumber());
        assertThat(studentDtos.getTotalElements()).isEqualTo(studentDtoFakePage.getTotalElements());
    }

}
