package com.example.controller;

import com.example.dto.StudentDto;
import com.example.exception.StudentNotFoundException;
import com.example.response.Response;
import com.example.service.StudentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = StudentController.class)
public class StudentControllerTest extends AbstractTestController{

    @MockBean
    private StudentService studentService;

    private static final String baseUri = "/api/v1/students";

    private StudentDto studentDto;

    final int PAGE_NUMBER = 0;

    final int PAGE_SIZE = 3;

    final String[] SORT = new String[]{"studentId, desc"};

    final String STUDENT_ID = UUID.randomUUID().toString();

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

//    @Test
//    public void shouldGetPaginatedSortedStudents() throws Exception {
//
//        Page<StudentDto> studentDtoPage = new PageImpl<>(List.of(studentDto));
//        when(studentService
//                .findPaginatedSortedStudents(studentDto.getStudentName(), PAGE_NUMBER, PAGE_SIZE, SORT))
//                .thenReturn(studentDtoPage);
//
//        doRequestGetPaginatedSortedStudents()
//                .andExpect(status().isOk());
//    }

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
                .andExpect(jsonPath("$.studentName").value(STUDENT_ID));
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
        doNothing().when(studentService).saveStudent(studentDto);

        doRequestSaveStudent(studentDto)
                .andExpect(status().isOk());
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
                        .content(getObjectMapper()
                                .writeValueAsString(studentDto)));
    }

}
