package com.example.controller;

import com.example.config.WebMvcConfiguration;
import com.example.dto.SubjectDto;
import com.example.model.status.ModelStatus;
import com.example.service.SubjectService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = SubjectController.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Import(WebMvcConfiguration.class)
public class SubjectControllerTest extends AbstractTestController {

    @MockBean
    private SubjectService subjectService;

    private SubjectDto subjectDto;

    private static final String baseUri = "/api/v1/subjects";

    private static final String SUBJECT_NAME = "Math";

    private static final String SUBJECT_ID = UUID.randomUUID().toString();

    final int PAGE_SIZE = 3;

    final String[] SORT = new String[]{"subjectId, desc"};

    @Before
    public void init(){
        subjectDto = SubjectDto
                        .builder()
                .subjectId(SUBJECT_ID)
                .subjectName(SUBJECT_NAME)
                .build();
    }

    @Test
    public void shouldGetSubjectById() throws Exception {

        when(subjectService.findSubjectById(SUBJECT_ID, ModelStatus.ACTIVE.getStatusCode()))
                .thenReturn(subjectDto);

        doRequestGetSubjectById()
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetSubjectByName() throws Exception {

        when(subjectService
                .findSubjectByName(SUBJECT_NAME, ModelStatus.ACTIVE.getStatusCode()))
                .thenReturn(subjectDto);

        doReqestGetSubjectByName()
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteSubjectById() throws Exception {
        doNothing()
                .when(subjectService)
                .deleteSubjectById(SUBJECT_ID);

        doRequestDeleteSubjectById()
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSaveSubject() throws Exception {
        when(subjectService
                .saveSubject(subjectDto))
                .thenReturn(subjectDto);

        doRequestSaveSubject(subjectDto)
                .andExpect(status().isCreated());
    }

    @Test
    public void whenNullField_throwException() throws Exception {
        final SubjectDto invalidSubject = SubjectDto
                .builder()
                .subjectName(null)
                .build();

        doRequestSaveSubject(invalidSubject)
                .andExpect(status().isBadRequest());
    }

    private ResultActions doReqestGetSubjectByName() throws Exception {
        return getMockMvc()
                .perform(get(baseUri)
                        .param("subjectName", SUBJECT_NAME)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions doRequestGetSubjectById() throws Exception {
        return getMockMvc()
                .perform(get(baseUri + "/{subjectId}", SUBJECT_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions doRequestDeleteSubjectById() throws Exception {
        return getMockMvc()
                .perform(delete(baseUri + "/{subjectId}", SUBJECT_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions doRequestSaveSubject(final SubjectDto subjectDto) throws Exception {
        return getMockMvc()
                .perform(post(baseUri)
                        .content(getObjectMapper().writeValueAsString(subjectDto))
                        .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
    }

}
