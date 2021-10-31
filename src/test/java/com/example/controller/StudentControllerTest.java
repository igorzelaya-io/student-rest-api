package com.example.controller;

import com.example.config.WebMvcConfiguration;
import com.example.dto.StudentDto;
import com.example.service.StudentService;
import com.example.service.pagingSorting.StudentPagingSortingService;
import com.example.utils.SortingPagingUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = StudentController.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Import(WebMvcConfiguration.class)
public class StudentControllerTest extends AbstractTestController{

    @MockBean
    private StudentService studentService;

    @Autowired
    private SortingPagingUtils sortingPagingUtils;

    @MockBean
    private StudentPagingSortingService studentPagingSortingService;

    private static final String baseUri = "/api/v1/students";

    private StudentDto studentDto;

    final int PAGE_NUMBER = 0;

    final int PAGE_SIZE = 3;

    final String[] SORT = new String[]{"studentId, desc"};

    private static final String STUDENT_ID = UUID.randomUUID().toString();

    private static final String STUDENT_NAME = "Igor Zelaya";

    @Before
    public void init(){
        studentDto = StudentDto
                .builder()
                .studentId(STUDENT_ID)
                .studentName("Igor Zelaya")
                .studentAge(18)
                .studentEmail("a@b.com")
                .studentSubjects(new ArrayList<>())
                .build();
    }

    @Test
    public void shouldGetStudentById() throws Exception {
        when(studentService.findStudentById(STUDENT_ID))
                .thenReturn(studentDto);

        doRequestGetStudentById()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(STUDENT_ID));
    }

    @Test
    public void shouldGetStudentByName() throws Exception {
        when(studentService.findStudentByName(studentDto.getStudentName()))
                .thenReturn(studentDto);

        doRequestGetStudentByName(studentDto.getStudentName())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentName").value(STUDENT_NAME));
    }

    @Test
    public void shouldDeleteById() throws Exception {
        doNothing().when(studentService)
                .deleteStudentById(STUDENT_ID);

        doRequestDeleteStudentById()
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSaveUser() throws Exception {
        when(studentService.saveStudent(studentDto))
                .thenReturn(studentDto);

        doRequestSaveStudent(studentDto)
                .andExpect(status().isCreated());
    }

    @Test
    public void whenNullValue_thenReturnBadRequest() throws Exception {
        StudentDto dto = StudentDto
                .builder()
                .studentName(null)
                .studentAge(20)
                .studentEmail("a@b.com")
                .build();
        doRequestSaveStudent(dto)
                .andExpect(status().isBadRequest());
    }

    private ResultActions doRequestGetPaginatedSortedStudents() throws Exception {
        return getMockMvc()
                .perform(get(baseUri)
                        .param("studentName", studentDto.getStudentName())
                        .param("page", String.valueOf(PAGE_NUMBER))
                        .param("size", String.valueOf(PAGE_SIZE))
                        .param("sort", SORT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions doRequestGetStudentById() throws Exception {
        return getMockMvc()
                .perform(get(baseUri + "/{studentId}", STUDENT_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions doRequestGetStudentByName(final String studentName) throws Exception {
        return getMockMvc()
                .perform(get(baseUri)
                        .param("studentName", studentName)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions doRequestDeleteStudentById() throws Exception {
        return getMockMvc()
                .perform(delete(baseUri + "/{studentId}", STUDENT_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions doRequestSaveStudent(final StudentDto studentDto) throws Exception {
        return getMockMvc()
                .perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper()
                                .writeValueAsString(studentDto)));
    }

}
