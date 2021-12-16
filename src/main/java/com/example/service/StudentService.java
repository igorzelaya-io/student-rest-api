package com.example.service;

import com.example.dto.StudentDto;
import com.example.dto.SubjectDto;
import com.example.exception.StudentNotFoundException;
import org.springframework.data.domain.Page;

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
    StudentDto saveStudent(StudentDto studentDto);

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

    /**
     * Return a page of sorted students.
     * @param studentName Student names to sort by.
     * @param page Page number to query by.
     * @param size Page size to query by.
     * @param sort Extra sort params to sort by.
     * @return PageResponseDto Student.
     */
    Page<StudentDto> findPaginatedSortedStudents(String studentName, int page, int size, String[] sort);

    /**
     * Add a Subject to student's subjectList
     * @param studentId
     * @param subjectDto
     */
    void addSubjectToStudent(String studentId, SubjectDto subjectDto);

    /**
     *
     * @param studentId
     * @param subjectId
     */
    void removeSubjectFromStudent(String studentId, String subjectId);
}
