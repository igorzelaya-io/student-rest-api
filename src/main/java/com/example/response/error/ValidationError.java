package com.example.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ValidationError extends SubError {

    private String object;

    private String field;

    private Object rejectedValue;

    private String message;

    public ValidationError(String object, String message){
        this.object = object;
        this.message = message;
    }

}
