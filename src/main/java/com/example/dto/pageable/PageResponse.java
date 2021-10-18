package com.example.dto.pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Interface for Paginated Response.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @param <T>
 */
public interface PageResponse<T> {

    Integer getPageSize();
    void setPageSize(Integer pageSize);

    Integer getNumberOfElements();
    void setNumberOfElements(Integer numberOfElements);

    int getTotalPages();
    void setTotalPages(Integer totalPages);

    Integer getPageNumber();
    void setPageNumber(Integer pageNumber);

    List<T> getPagePayload();
    void setPagePayload(List<T> pagePayload);

    /**
     * Factory method to return ResponseEntity with fields.
     * @param pageSize Integer
     * @param numberOfElements Integer
     * @param totalPages Integer
     * @param pageNumber Integer
     * @param pagePayload Integer
     * @return ResponseEntity
     */
    default ResponseEntity<? extends PageResponse<T>> buildResponseEntity
            (Integer pageSize, Integer numberOfElements, Integer totalPages,
             Integer pageNumber, List<T> pagePayload){
        this.setPageSize(pageSize);
        this.setNumberOfElements(numberOfElements);
        this.setTotalPages(totalPages);
        this.setPageNumber(pageNumber);
        this.setPagePayload(pagePayload);
        return new ResponseEntity<>(this, HttpStatus.OK);
    }

}
