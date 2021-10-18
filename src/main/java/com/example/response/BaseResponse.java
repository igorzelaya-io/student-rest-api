package com.example.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.Instant;

@JsonSerialize
public class BaseResponse <T> implements Response<T>{

    @JsonProperty("time")
    private Instant time;

    @JsonProperty("httpStatusCode")
    private Integer httpStatusCode;

    @JsonProperty("payload")
    private T payload;

    @JsonProperty("message")
    private String message;

    public BaseResponse(){
        super();
    }

    @Override
    public Instant getTime() {
        return this.time;
    }

    @Override
    public void setTime(Instant time) {
        this.time = time;
    }

    @Override
    public Integer getHttpStatusCode() {
        return this.httpStatusCode;
    }

    @Override
    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public T getPayload() {
        return this.payload;
    }

    @Override
    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
