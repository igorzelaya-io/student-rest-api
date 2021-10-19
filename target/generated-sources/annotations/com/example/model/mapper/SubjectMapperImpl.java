package com.example.model.mapper;

import com.example.dto.SubjectDto;
import com.example.dto.SubjectDto.SubjectDtoBuilder;
import com.example.model.Student;
import com.example.model.Subject;
import com.example.model.Subject.SubjectBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-19T15:47:50-0600",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.10 (Oracle Corporation)"
)
@Component
public class SubjectMapperImpl implements SubjectMapper {

    @Override
    public Subject dtoToSubject(SubjectDto dto) {
        if ( dto == null ) {
            return null;
        }

        SubjectBuilder subject = Subject.builder();

        subject.subjectId( dto.getSubjectId() );
        subject.subjectName( dto.getSubjectName() );
        List<Student> list = dto.getSubjectStudents();
        if ( list != null ) {
            subject.subjectStudents( new ArrayList<Student>( list ) );
        }
        subject.teacher( dto.getTeacher() );

        return subject.build();
    }

    @Override
    public SubjectDto toSubjectDto(Subject subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectDtoBuilder subjectDto = SubjectDto.builder();

        subjectDto.subjectId( subject.getSubjectId() );
        subjectDto.subjectName( subject.getSubjectName() );
        List<Student> list = subject.getSubjectStudents();
        if ( list != null ) {
            subjectDto.subjectStudents( new ArrayList<Student>( list ) );
        }
        subjectDto.teacher( subject.getTeacher() );

        return subjectDto.build();
    }
}
