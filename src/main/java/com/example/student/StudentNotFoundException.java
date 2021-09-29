package com.example.student;

import com.example.exception.ResourceNotFoundException;

/**
 * Student 404 status exception
 * @author Igor A. Zelaya
 */
public class StudentNotFoundException extends ResourceNotFoundException {

    /**
     *
     * @param field
     * @param fieldValue
     * @return
     */
    public static ResourceNotFoundException
            buildStudentNotFoundExceptionForField(String field, String fieldValue){
        return resourceNotFoundExceptionOf(Student.class, field, fieldValue);
    }

    /**
     *
     * @param studentId
     * @return
     */
    public static ResourceNotFoundException
            buildStudentNotFoundExceptionForId(String studentId){
        return resourceNotFoundExceptionOf(Student.class, "studentId", studentId);
    }

    /**
     *
     * @param searchParams
     * @return ResourceNotFoundException instance
     */
    public static ResourceNotFoundException buildStudentNotFoundException(String... searchParams){
        return resourceNotFoundExceptionOf(Student.class, searchParams);
    }

}