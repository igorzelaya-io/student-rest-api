package com.example.subject;

import com.example.exception.ResourceNotFoundException;

/**
 * Subject 404 status exception
 * @author Igor A. Zelaya
 */
public class SubjectNotFoundException extends ResourceNotFoundException {

    /**
     *
     * @param field
     * @param fieldValue
     * @return ResourceNotFoundException
     */
    public static ResourceNotFoundException buildSubjectNotFoundExceptionForField
            (String field, String fieldValue){
        return resourceNotFoundExceptionOf(Subject.class, field, fieldValue);
    }

    /**
     *
     * @param subjectId
     * @return ResourceNotFoundException
     */
    public static ResourceNotFoundException
            buildSubjectNotFoundExceptionForId(String subjectId){
       return resourceNotFoundExceptionOf(Subject.class, "subjectId", subjectId);
    }

    /**
     *
     * @param searchParams
     * @return ResourceNotFoundException
     */
    public static ResourceNotFoundException
            buildSubjectNotFoundException(String... searchParams){
        if(searchParams.length % 2 != 0)
            throw new IllegalArgumentException("Invalid Parameters");
        return resourceNotFoundExceptionOf(Subject.class, searchParams);
    }

}
