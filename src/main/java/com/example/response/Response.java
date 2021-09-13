package com.example.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public interface Response<T>{

    Instant getTime();
    void setTime(Instant instant);

    Integer getHttpStatusCode();
    void setHttpStatusCode(Integer httpStatusCode);

    T getPayload();
    void setPayload(T payload);

    String getMessage();
    void setMessage(String message);

    default ResponseEntity<? extends Response<T>> buildResponseEntity(HttpStatus httpStatus, String message, T payload){
        this.setTime(Instant.now());
        this.setHttpStatusCode(httpStatus.value());
        this.setMessage(message);
        this.setPayload(payload);
        return new ResponseEntity<>(this, httpStatus);
    }

}
