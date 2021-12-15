package com.example.service;

import com.example.config.WebMvcConfiguration;
import com.example.dto.TeacherDto;
import com.example.model.mapper.TeacherMapper;
import com.example.repository.TeacherRepository;
import com.example.utils.Messages;
import com.example.utils.SortingPagingUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    private TeacherDto teacherDto;



}
