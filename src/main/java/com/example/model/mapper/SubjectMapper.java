package com.example.model.mapper;

import com.example.dto.SubjectDto;
import com.example.model.Subject;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    Subject dtoToSubject(SubjectDto dto);

    SubjectDto toSubjectDto(Subject subject);

    List<Subject> dtosToSubjects(List<SubjectDto> dtos);

    List<SubjectDto> subjectsToDtos(List<Subject> subjects);

}
