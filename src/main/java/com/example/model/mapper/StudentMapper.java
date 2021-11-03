package com.example.model.mapper;

import com.example.dto.StudentDto;
import com.example.model.Student;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student dtoToStudent(StudentDto dto);

    StudentDto studentToDto(Student student);

    List<Student> dtoToStudent(List<StudentDto> dtos);

    List<StudentDto> studentToDto(List<Student> students);

}
