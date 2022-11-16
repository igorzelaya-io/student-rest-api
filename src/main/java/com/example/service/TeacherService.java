package com.example.service;

import com.example.dto.SubjectDto;
import com.example.dto.TeacherDto;
import org.springframework.data.domain.Page;

/**
 * Service interface for Teacher entity crud operations
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
public interface TeacherService {

    /**
     * Saves given Teacher into Db.
     * @param teacherDto
     */
    TeacherDto saveTeacher(TeacherDto teacherDto);

    /**
     * Find a Teacher by its ID.
     * @param teacherId
     * @return TeacherDto
     * @throws TeacherNotFoundException
     */
    TeacherDto findTeacherById(final String teacherId) throws TeacherNotFoundException;

    /**
     * Find Teacher by its name.
     * @param teacherName
     * @return TeacherDto
     * @throws TeacherNotFoundException
     */
    TeacherDto findTeacherByName(final String teacherName) throws TeacherNotFoundException;


    /**
     * Delete Teacher by its ID.
     * @param teacherId
     */
    void deleteTeacherById(final String teacherId);


    /**
     * Add A Subject to TeacherSubjects List.
     * @param subjectDto SubjectDto
     */
    void addSubjectToTeacher(final String teacherId, SubjectDto subjectDto);

    /**
     * If exists in teacher, remove from list.
     * @param teacherId String
     * @param subjectId String
     */
    void removeSubjectFromTeacher(final String teacherId, final String subjectId);


    /**
     * Returns a page of Teachers given query data.
     * @param teacherName String
     * @param page int
     * @param size int
     * @param sort Sorting parameters
     * @return Page TeacherDto
     */
    Page<TeacherDto> findPaginatedSortedTeachers(String teacherName, int page, int size, String[] sort);
}
