package com.example.model.mapper;

import com.example.dto.SubjectDto;
import com.example.model.Subject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    Subject dtoToSubject(SubjectDto dto);

    SubjectDto toSubjectDto(Subject subject);

}
