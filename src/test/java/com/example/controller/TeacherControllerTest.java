package com.example.controller;

import com.example.config.WebMvcConfiguration;
import com.example.dto.SubjectDto;
import com.example.dto.TeacherDto;
import com.example.model.mapper.TeacherMapper;
import com.example.repository.TeacherRepository;
import com.example.service.SubjectService;
import com.example.service.TeacherService;
import com.example.utils.SortingPagingUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration testing class for TeacherController.
 * @author Igor A. Zelaya
 */
@ContextConfiguration(classes = TeacherController.class )
@RunWith(SpringJUnit4ClassRunner.class)
@Import(WebMvcConfiguration.class)
public class TeacherControllerTest extends AbstractTestController{

    @MockBean
    private TeacherRepository teacherRepository;

    @Autowired
    private SortingPagingUtils sortingPagingUtils;

    private TeacherMapper studentMapper = Mappers.getMapper(TeacherMapper.class);

    @MockBean
    private SubjectService subjectService;

    @MockBean
    private TeacherService teacherService;

    private static final String baseUri = "/api/v1/teachers";

    private TeacherDto teacherDto;

    private SubjectDto subjectDto;

    final int PAGE_NUMBER = 0;

    final int PAGE_SIZE = 3;

    final String[] SORT = new String[]{"teacherId, desc"};

    private final static String TEACHER_ID = UUID.randomUUID().toString();

    private static final String SUBJECT_ID = UUID.randomUUID().toString();

    private final static String TEACHER_NAME = "Igor Zelaya";

    @Before
    public void init(){
        teacherDto =
                TeacherDto.builder()
                        .teacherId(TEACHER_ID)
                        .teacherName(TEACHER_NAME)
                        .teacherAge(20)
                        .teacherEmail("i@b.com")
                        .build();
        subjectDto =
                SubjectDto
                        .builder()
                        .subjectId(SUBJECT_ID)
                        .subjectName("Math")
                        .build();
    }

    @Test
    @Ignore
    public void shouldGetPaginatedSortedStudents() throws Exception {
        Page<TeacherDto> fakeTeacherPage = new PageImpl<>(List.of(teacherDto));
        when(teacherService
                .findPaginatedSortedTeachers(TEACHER_NAME, PAGE_NUMBER, PAGE_SIZE, SORT))
                .thenReturn(fakeTeacherPage);

        doRequestGetPaginatedSortedTeachers()
                .andExpect(status().isOk());

    }

    @Test
    public void shouldGetTeacherById() throws Exception {
        when(teacherService.findTeacherById(TEACHER_ID))
                .thenReturn(teacherDto);

        doRequestGetTeacherById()
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetTeacherByName() throws Exception {
        when(teacherService.findTeacherByName(TEACHER_NAME))
                .thenReturn(teacherDto);

        doRequestGetTeacherByName()
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteTeacherById() throws Exception {
        doNothing()
                .when(teacherService)
                .deleteTeacherById(TEACHER_ID);

        doRequestDeleteTeacherById()
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSaveTeacher() throws Exception {
        when(teacherService.saveTeacher(teacherDto))
                .thenReturn(teacherDto);

        doRequestSaveTeacher(teacherDto)
                .andExpect(status().isCreated());
    }

    @Test
    public void whenNullValue_thenReturnBadRequest() throws Exception {
        TeacherDto teacherDto = TeacherDto
                .builder()
                .teacherName(null)
                .teacherAge(20)
                .teacherEmail("i@z.com")
                .build();

        doRequestSaveTeacher(teacherDto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldAddSubjectToTeacher() throws Exception {
        doNothing()
                .when(teacherService)
                .addSubjectToTeacher(TEACHER_ID, subjectDto);

        doRequestAddSubjectToTeacher(subjectDto)
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRemoveSubjectFromTeacher() throws Exception {
        doNothing()
                .when(teacherService)
                .removeSubjectFromTeacher(TEACHER_ID, SUBJECT_ID);

        doRequestRemoveSubjectFromTeacher()
                .andExpect(status().isOk());
    }

    private ResultActions doRequestGetPaginatedSortedTeachers() throws Exception {
        return getMockMvc()
                .perform(get(baseUri)
                        .param("teacherName", TEACHER_NAME)
                        .param("page", String.valueOf(PAGE_NUMBER))
                        .param("size", String.valueOf(PAGE_SIZE))
                        .param("sort", SORT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions doRequestGetTeacherById() throws Exception {
        return getMockMvc()
                .perform(get(baseUri + "/{teacherId}", TEACHER_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions doRequestGetTeacherByName() throws Exception {
        return getMockMvc()
                .perform(get(baseUri)
                        .param("teacherName", TEACHER_NAME)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions doRequestDeleteTeacherById() throws Exception {
        return getMockMvc()
                .perform(delete(baseUri + "/{teacherId}", TEACHER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions doRequestSaveTeacher(final TeacherDto teacherDto) throws Exception {
        return getMockMvc()
                .perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper()
                                .writeValueAsString(teacherDto)));
    }

    private ResultActions doRequestAddSubjectToTeacher(final SubjectDto subjectDto) throws Exception {
        return getMockMvc()
                .perform(post(baseUri + "/{teacherId}/subjects", TEACHER_ID)
                        .content(getObjectMapper().writeValueAsString(subjectDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions doRequestRemoveSubjectFromTeacher() throws Exception {
        return getMockMvc()
                .perform(delete(baseUri + "/{teacherId}/subjects/{subjectId}"
                , TEACHER_ID, SUBJECT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }
}
