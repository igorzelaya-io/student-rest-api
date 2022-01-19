package com.example.utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageKey {

    //Not found.
    STUDENT_NOT_FOUND("student-not-found"),
    TEACHER_NOT_FOUND("teacher-not-found"),
    SUBJECT_NOT_FOUND("subject-not-found"),

    //Teacher Service
    TEACHER_INVALID_SUBJECT("teacher-subject-invalid"),

    //Student Service
    INVALID_STUDENT_SUBJECT("student-subject-invalid");

    public final String key;

    public String getKey(){
        return this.key;
    }

}
