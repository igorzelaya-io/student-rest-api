package com.example.service;

import com.example.dto.StudentDto;
import com.example.exception.StudentNotFoundException;

/**
 * Service interface for Student entity crud operations.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
public interface StudentService {

    /**
     * Saves given Student into DB.
     * @param studentDto Student
     */
    void saveStudent(StudentDto studentDto);

    /**
     * Find a student by its ID.
     * @param studentId String
     * @return Student StudentDto
     * @throws StudentNotFoundException when no Student is found by ID
     */
    StudentDto findStudentById(final String studentId) throws StudentNotFoundException;

    /**
     * Find a student by its name.
     * @param studentName String
     * @return Student StudentDto
     * @throws StudentNotFoundException when no Student is found by name
     */
    StudentDto findStudentByName(final String studentName) throws StudentNotFoundException;


    /**
     * Delete student by its ID.
     * @param studentId
     */
    void deleteStudentById(final String studentId);


}
