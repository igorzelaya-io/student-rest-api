package com.example.model.mapper;

import com.example.dto.TeacherDto;
import com.example.model.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    Teacher dtoToTeacher(TeacherDto dto);

    TeacherDto toTeacherDto(Teacher teacher);

}
