package com.example.service;

import com.example.dto.SubjectDto;
import com.example.exception.SubjectNotFoundException;
import com.example.model.Student;
import com.example.model.Subject;
import com.example.model.mapper.StudentMapper;
import com.example.model.mapper.SubjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

/**
 * Interface Service for Subject entity.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
public interface SubjectService {

    /**
     * Save Subject entity.
     * @param subjectDto SubjectDto
     */
    SubjectDto saveSubject(SubjectDto subjectDto);


    /**
     * Find a Subject by its ID.
     * @param subjectId
     * @throws SubjectNotFoundException
     */
    SubjectDto findSubjectById(final String subjectId) throws SubjectNotFoundException;


    /**
     * Find Subject by name.
     * @param subjectName
     * @return
     * @throws SubjectNotFoundException
     */
    SubjectDto findSubjectByName(final String subjectName) throws SubjectNotFoundException;

    /**
     * Delete Subject, in reality it just modifies Subject Status Code.
     * @param subjectId
     */
    void deleteSubjectById(final String subjectId);

    /**
     * Find sorted and paginated SubjectDto.
     * @param subjectName String
     * @param page page number
     * @param size page size
     * @param sort Sorting parameters
     * @return Page SubjectDto
     */
    Page<SubjectDto> findBySubjectNameContaining
    (final String subjectName, final int page, final int size, final String[] sort);

    /**
     * Evaluate if subject exists by subjectName.
     * @param subjectName String
     * @return  boolean
     */
    boolean subjectExists(String subjectName);


    /**
     * Update Subject.
     * @param subject
     */
    void updateSubject(Subject subject);

    /**
     * Util method for mapping Dtos to Subject entity.
     * @param subjectDto Dto to convert
     * @return Subject instance
     */
    static Subject dtoToSubject(SubjectDto subjectDto){
        SubjectMapper mapper = Mappers.getMapper(SubjectMapper.class);
        return mapper.dtoToSubject(subjectDto);
    }

}
