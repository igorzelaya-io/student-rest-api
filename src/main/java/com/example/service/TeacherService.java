package com.example.service;

import com.example.dto.TeacherDto;
import com.example.exception.TeacherNotFoundException;

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
    void saveTeacher(TeacherDto teacherDto);

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

}
