package com.example.teacher;

import com.example.exception.ResourceNotFoundException;

public class TeacherNotFoundException extends ResourceNotFoundException {

    public static ResourceNotFoundException buildTeacherNotFoundExceptionForId(String teacherId){
        return resourceNotFoundExceptionOf(Teacher.class, "teacherId", teacherId);
    }

    public static ResourceNotFoundException buildTeacherNotFoundExceptionForField(String field, String fieldValue){
        return resourceNotFoundExceptionOf(Teacher.class, field, fieldValue);
    }

    public static ResourceNotFoundException buildTeacherNotFoundException(String... searchParams){
        return resourceNotFoundExceptionOf(Teacher.class, searchParams);
    }

}
