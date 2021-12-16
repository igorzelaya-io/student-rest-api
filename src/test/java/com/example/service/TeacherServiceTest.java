package com.example.service;

import com.example.config.WebMvcConfiguration;
import com.example.dto.StudentDto;
import com.example.dto.SubjectDto;
import com.example.dto.TeacherDto;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.TeacherNotFoundException;
import com.example.model.Subject;
import com.example.model.Teacher;
import com.example.model.mapper.SubjectMapper;
import com.example.model.mapper.TeacherMapper;
import com.example.model.status.ModelStatus;
import com.example.repository.SubjectRepository;
import com.example.repository.TeacherRepository;
import com.example.service.implementation.SubjectServiceImpl;
import com.example.service.implementation.TeacherServiceImpl;
import com.example.utils.MessageKey;
import com.example.utils.Messages;
import com.example.utils.SortingPagingUtils;
import org.junit.Before;
import static org.assertj.core.api.Assertions.assertThat;
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

import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@Import({Messages.class, WebMvcConfiguration.class})
public class TeacherServiceTest {

    @Autowired
    private Messages messages;

    @Autowired
    private SortingPagingUtils sortingPagingUtils;

    private TeacherRepository teacherRepository;

    private TeacherService teacherService;

    private TeacherMapper teacherMapper;

    private SubjectMapper subjectMapper;

    private SubjectService subjectService;

    private SubjectRepository subjectRepository;

    private TeacherDto teacherDto;

    private Teacher teacher;

    private SubjectDto subjectDto;

    private Subject subject;

    private static final String TEACHER_ID = UUID.randomUUID().toString();

    private static final String SUBJECT_ID = UUID.randomUUID().toString();

    private static final String SUBJECT_NAME = "Math";

    private static final String TEACHER_NAME = "Igor Zelaya";

    private static final String TEACHER_EMAIL = "a@b.com";

    private String TEACHER_NOT_FOUND;

    @Before
    public void init(){

        teacherRepository = Mockito.mock(TeacherRepository.class);

        subjectRepository = Mockito.mock(SubjectRepository.class);

        teacherMapper = Mappers.getMapper(TeacherMapper.class);

        subjectMapper = Mappers.getMapper(SubjectMapper.class);

        subjectService = new SubjectServiceImpl(subjectMapper, subjectRepository, sortingPagingUtils);

        teacherService = new TeacherServiceImpl(teacherRepository, teacherMapper,
                subjectService, subjectMapper, sortingPagingUtils, messages);

        TEACHER_NOT_FOUND = messages
                .getMessage(MessageKey.TEACHER_NOT_FOUND.getKey());

        teacher = Teacher
                .builder()
                .teacherId(TEACHER_ID)
                .teacherName(TEACHER_NAME)
                .teacherAge(30)
                .teacherEmail(TEACHER_EMAIL)
                .teacherStatus(ModelStatus.ACTIVE)
                .teacherSubjects(new ArrayList<>())
                .build();

        subjectDto = SubjectDto
                .builder()
                .subjectId(SUBJECT_ID)
                .subjectName(SUBJECT_NAME)
                .teacher(null)
                .subjectStudents(new ArrayList<>())
                .build();

        subject = Subject
                .builder()
                .subjectId(SUBJECT_ID)
                .subjectName(SUBJECT_NAME)
                .teacher(null)
                .subjectStudents(new ArrayList<>())
                .build();

        teacherDto = TeacherDto
                .builder()
                .teacherName(TEACHER_NAME)
                .teacherId(TEACHER_ID)
                .teacherAge(20)
                .teacherEmail(TEACHER_EMAIL)
                .build();
    }

    @Test
    public void shouldReturnTeacherById(){

        doReturn(Optional.of(teacher))
                .when(teacherRepository)
                .findById(TEACHER_ID);

        TeacherDto teacherDto = teacherService.findTeacherById(TEACHER_ID);

        assertThat(teacherDto).isNotNull();
        assertThat(teacherDto.getTeacherId()).isEqualTo(teacher.getTeacherId());
        assertThat(teacherDto.getTeacherName()).isEqualTo(teacher.getTeacherName());

        assertThat(teacherDto.getTeacherEmail()).isEqualTo(teacher.getTeacherEmail());
    }

    @Test
    public void whenNotFoundById_throwException(){
        Map<String, String> params = Map.of("teacherId", TEACHER_ID);

        final String EX_MESSAGE = new StringBuilder(TEACHER_NOT_FOUND)
                .append(" ")
                .append(params).toString();


        doReturn(Optional.empty())
                .when(teacherRepository)
                .findById(TEACHER_ID);

        try {
            TeacherDto teacherDto = teacherService.findTeacherById(TEACHER_ID);
            fail("Exception must be thrown.");
        }
        catch (Exception e) {
            assertThat(e).isInstanceOf(ResourceNotFoundException.class);
            assertThat(e.getMessage()).isEqualTo(EX_MESSAGE);
        }
    }

    @Test
    public void shouldReturnTeacherByName() {
        when(teacherRepository.findByTeacherName(TEACHER_NAME))
                .thenReturn(Optional.of(teacher));

        TeacherDto teacherDto = teacherService.findTeacherByName(TEACHER_NAME);

        assertThat(teacherDto).isNotNull();
        assertThat(teacherDto.getTeacherId()).isEqualTo(teacher.getTeacherId());
        assertThat(teacherDto.getTeacherName()).isEqualTo(teacher.getTeacherName());

        assertThat(teacherDto.getTeacherEmail()).isEqualTo(teacher.getTeacherEmail());
    }

    @Test
    public void whenNotFoundByName_throwException(){
        Map<String, String> params = Map.of("teacherName", TEACHER_NAME);

        final String EX_MESSAGE = new StringBuilder(TEACHER_NOT_FOUND)
                .append(" ")
                .append(params).toString();

        when(teacherRepository.findByTeacherName(TEACHER_NAME))
                .thenReturn(Optional.empty());

        try {
            TeacherDto teacherDto = teacherService.findTeacherByName(TEACHER_NAME);
            fail("Exception must be thrown");
        }
        catch (Exception e){
            assertThat(e).isInstanceOf(ResourceNotFoundException.class);
            assertThat(e.getMessage()).isEqualTo(EX_MESSAGE);
        }
    }

    @Test
    public void getPaginatedTeachers(){
        Page<Teacher> teacherFakePage = new PageImpl<>(List.of(teacher));

        List<Sort.Order> sortOrder = List.of(new Sort.Order(Sort.Direction.DESC, "teacherId"));
        Pageable pageable = PageRequest.of(0, 3, Sort.by(sortOrder));


        when(teacherRepository.findByTeacherNameContaining(TEACHER_NAME, pageable))
                .thenReturn(teacherFakePage);

        Page<TeacherDto> teacherDtos = teacherService
                .findPaginatedSortedTeachers
                        (TEACHER_NAME, 0, 3, new String[]{"teacherId", "desc"});

        assertThat(teacherDtos).isNotNull();
        assertThat(teacherDtos.getSize()).isEqualTo(teacherFakePage.getSize());
        assertThat(teacherDtos.getNumber()).isEqualTo(teacherFakePage.getNumber());
        assertThat(teacherDtos.getTotalElements()).isEqualTo(teacherFakePage.getTotalElements());
    }

    @Test
    public void shouldAddSubjectToTeacher_whenSubjectDoesntExist(){

        doReturn(Optional.of(teacher))
                .when(teacherRepository)
                        .findById(TEACHER_ID);

        doReturn(false)
                .when(subjectRepository)
                        .existsBySubjectNameIgnoreCase(SUBJECT_NAME);

        doReturn(null)
                .when(teacherRepository)
                        .save(teacher);

        teacherService.addSubjectToTeacher(teacher.getTeacherId(), subjectDto);

        teacher.addSubject(subject);

        assertThat(subject).isIn(teacher.getTeacherSubjects());
        assertThat(subject.getTeacher()).isNotNull();
        assertThat(subject.getTeacher().getTeacherId()).isEqualTo(teacher.getTeacherId());
    }

    @Test
    public void shouldRemoveSubjectFromTeacher(){
        when(teacherService.findTeacherById(TEACHER_ID))
                .thenReturn(teacherDto);

        when(subjectService.findSubjectById(SUBJECT_ID))
                .thenReturn(subjectDto);


    }
}
