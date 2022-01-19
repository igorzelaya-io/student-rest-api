package com.example.service;


import com.example.config.WebMvcConfiguration;
import com.example.dto.SubjectDto;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Subject;
import com.example.model.Teacher;
import com.example.model.mapper.SubjectMapper;
import com.example.model.status.ModelStatus;
import com.example.repository.SubjectRepository;
import com.example.service.implementation.SubjectServiceImpl;
import com.example.utils.MessageKey;
import com.example.utils.Messages;
import com.example.utils.SortingPagingUtils;
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
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;


@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@Import({Messages.class, WebMvcConfiguration.class })
public class SubjectServiceTest {

    @Autowired
    private Messages messages;

    @Autowired
    private SortingPagingUtils sortingPagingUtils;

    private SubjectService subjectService;

    private SubjectMapper subjectMapper;

    private SubjectRepository subjectRepository;

    private SubjectDto subjectDto;

    private Subject subject;

    private static final String SUBJECT_ID = UUID.randomUUID().toString();

    private static final String SUBJECT_NAME = "Math";

    private String SUBJECT_NOT_FOUND;

    @Before
    public void init(){
        subjectRepository = Mockito.mock(SubjectRepository.class);
        subjectMapper = Mappers.getMapper(SubjectMapper.class);
        subjectService = new SubjectServiceImpl(subjectMapper, subjectRepository, sortingPagingUtils);

        subjectDto = SubjectDto
                .builder()
                .subjectId(SUBJECT_ID)
                .subjectName(SUBJECT_NAME)
                .subjectStatus(ModelStatus.ACTIVE)
                .build();

        subject = Subject
                .builder()
                .subjectId(SUBJECT_ID)
                .subjectName(SUBJECT_NAME)
                .subjectStatus(ModelStatus.ACTIVE)
                .teacher(null)
                .build();

        SUBJECT_NOT_FOUND = messages
                .getMessage(MessageKey.TEACHER_NOT_FOUND.getKey());

    }

    @Test
    public void shouldReturnSubjectById(){
        doReturn(Optional.of(subject))
                .when(subjectRepository)
                .findById(SUBJECT_ID);

        SubjectDto subjectDto = subjectService.findSubjectById(SUBJECT_ID);

        assertThat(subjectDto).isNotNull();
        assertThat(subjectDto.getSubjectId()).isEqualTo(subject.getSubjectId());
        assertThat(subjectDto.getSubjectName()).isEqualTo(subject.getSubjectName());

    }

    @Test
    public void whenNotFoundById_throwException() {
        Map<String, String> params = Map.of("subjectId", SUBJECT_ID);

        final String EX_MESSAGE = new StringBuilder(SUBJECT_NOT_FOUND)
                .append(" ")
                .append(params).toString();

        doReturn(Optional.empty())
                .when(subjectService)
                .findSubjectById(SUBJECT_ID);

        try{
            SubjectDto subjectDto = subjectService.findSubjectById(SUBJECT_ID);
            fail("Exception should be thrown");
        }
        catch(Exception e) {
            assertThat(e).isInstanceOf(ResourceNotFoundException.class);
            assertThat(e.getMessage()).isEqualTo(EX_MESSAGE);
        }
    }

    @Test
    public void shouldReturnSubjectByName(){
        when(subjectRepository.findBySubjectNameContainingIgnoreCase(SUBJECT_NAME))
                .thenReturn(Optional.of(subject));

        SubjectDto subjectDto = subjectService.findSubjectByName(SUBJECT_NAME);

        assertThat(subjectDto).isNotNull();

        assertThat(subjectDto.getSubjectId()).isEqualTo(subject.getSubjectId());
        assertThat(subjectDto.getSubjectName()).isEqualTo(subject.getSubjectName());
    }

    @Test
    public void whenNotFoundByName_throwException(){
        Map<String, String> params = Map.of("subjectName", SUBJECT_NAME);

        final String EX_MESSAGE = new StringBuilder(SUBJECT_NOT_FOUND)
                .append(" ")
                .append(params).toString();

        when(subjectRepository
                .findBySubjectNameContainingIgnoreCase(SUBJECT_NAME))
                .thenReturn(Optional.empty());

        try{
            SubjectDto subjectDto = subjectService.findSubjectByName(SUBJECT_NAME);
            fail("Should throw exception when optional is empty");
        }
        catch(Exception e){
            assertThat(e).isInstanceOf(ResourceNotFoundException.class);
            assertThat(e.getMessage()).isEqualTo(EX_MESSAGE);
        }
    }

    @Test
    public void shouldReturnPaginatedSubjects(){
        Page<Subject> subjectFakePage = new PageImpl<>(List.of(subject));

        List<Sort.Order> sortOrder = List.of(new Sort.Order(Sort.Direction.DESC, "subjectId"));

        Pageable pageable = PageRequest.of(0, 3, Sort.by(sortOrder));

        when(subjectRepository.findBySubjectNameContaining(SUBJECT_NAME, pageable))
                .thenReturn(subjectFakePage);

        Page<SubjectDto> subjectDtos = subjectService
                .findBySubjectNameContaining(SUBJECT_NAME, 0, 3, new String[]{"subjectId", "desc"});

        assertThat(subjectDtos).isNotNull();
        assertThat(subjectDtos.getSize()).isEqualTo(subjectFakePage.getSize());
        assertThat(subjectDtos.getNumber()).isEqualTo(subjectFakePage.getNumber());
        assertThat(subjectDtos.getTotalElements()).isEqualTo(subjectFakePage.getTotalElements());

    }

}
