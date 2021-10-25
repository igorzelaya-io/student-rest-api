package com.example.utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageKey {

    HELLOWORLD("");

    public final String key;

    public String getKey(){
        return this.key;
    }

}
