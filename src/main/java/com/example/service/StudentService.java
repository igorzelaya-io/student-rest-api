package com.example.service;

import com.example.dto.StudentDto;
import com.example.dto.SubjectDto;
import com.example.exception.ResourceNotFoundException;
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
     */
    StudentDto findStudentById(final String studentId);

    /**
     * Find a student by its name.
     * @param studentName String
     * @return Student StudentDto
     */
    StudentDto findStudentByName(final String studentName);


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
     * @param subjectId SubjectId
     * @param subjectDto
     */
    void addSubjectToStudent(final String studentId, final String subjectId, final SubjectDto subjectDto);

    /**
     * Remove subject from student and vice versa.
     * @param studentId
     * @param subjectId
     */
    void removeSubjectFromStudent(String studentId, String subjectId);
}
