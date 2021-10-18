package com.example.service;

import com.example.dto.SubjectDto;
import com.example.exception.SubjectNotFoundException;

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
    void saveSubject(SubjectDto subjectDto);


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


}
