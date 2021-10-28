package com.example.exception;

import com.example.response.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

/**
 * Exception Handler for all thrown exceptions.
 * @author Igor A. Zelaya
 * @version 1.0.0
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class StudentApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter
            (MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String error = new StringBuilder(ex.getParameterName())
                .append( " parameter is name")
                .toString();
        return buildResponseEntity(new ErrorResponse(HttpStatus.BAD_REQUEST, error, ex));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported
            (HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        StringBuilder stringBuilder = new StringBuilder(ex.getContentType().toString())
                .append(" media type is not supported. Supported media types are: ");
        ex.getSupportedMediaTypes()
                .forEach(mediaType -> stringBuilder.append(mediaType).append(", "));

        return buildResponseEntity
                (new ErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                        stringBuilder.substring(0, stringBuilder.length() - 2), ex));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage("Validation error.");
        errorResponse.addValidationErrors(ex.getBindingResult().getFieldErrors());
        errorResponse.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        logger.info(
                String.format("%s to %s"
                        , servletWebRequest.getHttpMethod().toString()
                        , servletWebRequest.getRequest().getServletPath()));
        String error = "Malformed JSON request";
        return buildResponseEntity(new ErrorResponse(HttpStatus.BAD_REQUEST, error, ex));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Error writing JSON output";
        return buildResponseEntity(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = String.format("Could not find the %s method for the URL %s"
                , ex.getHttpMethod(), ex.getRequestURL());

        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage(error);
        response.setDebugMessage(ex.getMessage());
        return buildResponseEntity(response);
    }

    /**
     * When thrown ConstraintViolationException return this response.
     * @param ex ConstraintViolationException
     * @return ErrorResponse
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage("Validation error.");
        errorResponse.addValidationErrors(ex.getConstraintViolations());
        return buildResponseEntity(errorResponse);
    }

    /**
     * When thrown ResourceNotFoundException, return this response.
     * @param ex ResourceNotFoundException
     * @return ErrorResponse
     */
    @ExceptionHandler({ResourceNotFoundException.class})
    protected ResponseEntity handleResourceNotFoundException(ResourceNotFoundException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND);
        response.setMessage(ex.getMessage());
        return buildResponseEntity(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex){
        return buildResponseEntity(new ErrorResponse(HttpStatus.NOT_FOUND, ex));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation
            (DataIntegrityViolationException ex, WebRequest request){

        if(ex.getCause() instanceof ConstraintViolationException){
            return buildResponseEntity(new ErrorResponse(HttpStatus.CONFLICT, "Database error", ex.getCause()));
        }
        return buildResponseEntity(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch
            (MethodArgumentTypeMismatchException ex, WebRequest request){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND);
        response.setMessage(String
                .format("The parameter %s of value '%s' could not be converted to type '%s'"
                , ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));

        response.setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(response);
    }

    /**
     * Build ResponseEntity for Given ErrorResponse.
     * @param errorResponse built ErrorResponse
     * @return ResponseEntity of given response.
     */
    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse){
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }
}

