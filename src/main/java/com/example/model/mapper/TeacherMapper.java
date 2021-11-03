package com.example.model.mapper;

import com.example.dto.TeacherDto;
import com.example.model.Teacher;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    Teacher dtoToTeacher(TeacherDto dto);

    TeacherDto toTeacherDto(Teacher teacher);

    List<Teacher> dtosToTeachers(List<TeacherDto> dtos);

    List<TeacherDto> teachersToDtos(List<Teacher> teachers);

}
