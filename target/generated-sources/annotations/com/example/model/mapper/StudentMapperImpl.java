package com.example.model.mapper;

import com.example.dto.StudentDto;
import com.example.dto.StudentDto.StudentDtoBuilder;
import com.example.model.Student;
import com.example.model.Student.StudentBuilder;
import com.example.model.Subject;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-19T16:55:23-0600",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.10 (Oracle Corporation)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public Student dtoToStudent(StudentDto dto) {
        if ( dto == null ) {
            return null;
        }

        StudentBuilder student = Student.builder();

        student.studentId( dto.getStudentId() );
        student.studentName( dto.getStudentName() );
        student.studentEmail( dto.getStudentEmail() );
        student.studentAge( dto.getStudentAge() );
        List<Subject> list = dto.getStudentSubjects();
        if ( list != null ) {
            student.studentSubjects( new ArrayList<Subject>( list ) );
        }

        return student.build();
    }

    @Override
    public StudentDto studentToDto(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentDtoBuilder studentDto = StudentDto.builder();

        studentDto.studentId( student.getStudentId() );
        studentDto.studentName( student.getStudentName() );
        studentDto.studentAge( student.getStudentAge() );
        studentDto.studentEmail( student.getStudentEmail() );
        List<Subject> list = student.getStudentSubjects();
        if ( list != null ) {
            studentDto.studentSubjects( new ArrayList<Subject>( list ) );
        }

        return studentDto.build();
    }
}
