package com.example.response.error;

import com.example.utils.JsonLowerCaseResolver;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import javax.validation.ConstraintViolation;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class used to return API Error response.
 * @author Igor A. Zelaya
 * @version 1.0.1
 */
@JsonSerialize
@JsonTypeIdResolver(JsonLowerCaseResolver.class)
@Getter
@Setter
public class ErrorResponse {

    @JsonProperty("httpStatus")
    private HttpStatus httpStatus;

    @JsonProperty("date")
    private Instant date;

    @JsonProperty("message")
    private String message;

    @JsonProperty("debugMessage")
    private String debugMessage;

    @JsonProperty("subErrors")
    private List<SubError>subErrors = new ArrayList<>();

    private ErrorResponse(){
        this.date = Instant.now();
    }

    /**
     * HttpStatus constructor.
     * @param httpStatus
     */
    public ErrorResponse(HttpStatus httpStatus){
        this();
        this.httpStatus = httpStatus;
    }

    /**
     * HttpStatus and Throwable args constructor.
     * @param httpStatus
     * @param ex
     */
    public ErrorResponse(HttpStatus httpStatus, Throwable ex){
        this();
        this.httpStatus = httpStatus;
        this.message = "Unexpected error.";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ErrorResponse(HttpStatus httpStatus, String message, Throwable ex){
        this();
        this.httpStatus = httpStatus;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    /**
     * Add Sub Error to list.
     * @param subError SubError
     */
    private void addSubError(SubError subError) {
        this.subErrors.add(subError);
    }

    /**
     * Add validation Error to list
     * @param object object name
     * @param message error message
     */
    private void addValidationError(String object, String message) {
        this.addSubError(new ValidationError(object, message));
    }

    /**
     * add validation error to list.
     * @param object object name
     * @param field object field
     * @param rejectedValue rejectedObject
     * @param message error message
     */
    private void addValidationError
            (String object, String field, Object rejectedValue, String message) {
        this.addSubError(new ValidationError(object, field, rejectedValue, message));
    }

    /**
     * add FieldError to sub errors.
     * @param fieldError
     */
    private void addValidationError(FieldError fieldError) {
        this.addValidationError
                (fieldError.getObjectName(), fieldError.getField(),
                        fieldError.getRejectedValue(), fieldError.getDefaultMessage());
    }

    /**
     * Add object error to sub errors.
     * @param objectError ObjectError
     */
    private void addValidationError(ObjectError objectError) {
        this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
    }

    /**
     * Add a list of fieldErrors to Sub errors.
     * @param fieldErrors List FieldError
     */
    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors
                .forEach(error -> addValidationError(error));
    }

    /**
     * Add a list of Object Errors to Sub errors.
     * @param objectErrors
     */
    public void addValidationError(List<ObjectError> objectErrors) {
        objectErrors
                .forEach(error -> addValidationError(error));
    }


    /**
     * Add a constraint violation to sub errors.
     * @param constraintViolation
     */
    private void addValidationError(ConstraintViolation<?> constraintViolation) {
        this.addValidationError(constraintViolation.getRootBeanClass().getSimpleName(),
                constraintViolation.getPropertyPath().toString(),
                constraintViolation.getInvalidValue(),
                constraintViolation.getMessage());
    }

    /**
     * Add a Set of Constraint Violations to Sub errors.
     * @param constraintViolations
     */
    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations
                .forEach(violation -> addValidationError(violation));
    }

}
