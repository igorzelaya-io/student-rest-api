package com.example.model.mapper;

import com.example.dto.TeacherDto;
import com.example.dto.TeacherDto.TeacherDtoBuilder;
import com.example.model.Subject;
import com.example.model.Teacher;
import com.example.model.Teacher.TeacherBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-18T14:25:57-0600",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.10 (Oracle Corporation)"
)
@Component
public class TeacherMapperImpl implements TeacherMapper {

    @Override
    public Teacher dtoToTeacher(TeacherDto dto) {
        if ( dto == null ) {
            return null;
        }

        TeacherBuilder teacher = Teacher.builder();

        teacher.teacherId( dto.getTeacherId() );
        teacher.teacherName( dto.getTeacherName() );
        teacher.teacherEmail( dto.getTeacherEmail() );
        teacher.teacherAge( dto.getTeacherAge() );
        List<Subject> list = dto.getTeacherSubjects();
        if ( list != null ) {
            teacher.teacherSubjects( new ArrayList<Subject>( list ) );
        }

        return teacher.build();
    }

    @Override
    public TeacherDto toTeacherDto(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }

        TeacherDtoBuilder teacherDto = TeacherDto.builder();

        teacherDto.teacherId( teacher.getTeacherId() );
        teacherDto.teacherName( teacher.getTeacherName() );
        teacherDto.teacherAge( teacher.getTeacherAge() );
        teacherDto.teacherEmail( teacher.getTeacherEmail() );
        List<Subject> list = teacher.getTeacherSubjects();
        if ( list != null ) {
            teacherDto.teacherSubjects( new ArrayList<Subject>( list ) );
        }

        return teacherDto.build();
    }
}
