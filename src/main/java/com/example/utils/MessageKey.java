package com.example.utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageKey {

    //Not found.
    STUDENT_NOT_FOUND("student-not-found"),
    TEACHER_NOT_FOUND("teacher-not-found"),

    //Teacher Service
    INVALID_SUBJECT("teacher-subject-invalid");

    public final String key;

    public String getKey(){
        return this.key;
    }

}
