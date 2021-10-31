package com.example.utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageKey {

    //Not found.
    STUDENT_NOT_FOUND("student-not-found");

    public final String key;

    public String getKey(){
        return this.key;
    }

}
