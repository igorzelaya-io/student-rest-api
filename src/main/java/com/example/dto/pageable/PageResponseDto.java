package com.example.dto.pageable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Dto for PageResponse.
 * @author  Igor A. Zelaya (izelaya22@gmail.com)
 * @param <T>
 */
@Builder
@JsonSerialize
@NoArgsConstructor
public class PageResponseDto<T> implements PageResponse<T>{

    @JsonProperty("pageSize")
    private int pageSize;

    @JsonProperty("numberOfElements")
    private int  numberOfElements;

    @JsonProperty("totalPages")
    private int totalPages;

    @JsonProperty("pageNumber")
    private int pageNumber;

    @JsonProperty("pagePayload")
    private List<T> pagePayload;

    @Override
    public Integer getPageSize() {
        return this.pageSize;
    }

    @Override
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public Integer getNumberOfElements() {
        return this.numberOfElements;
    }

    @Override
    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    @Override
    public int getTotalPages() {
        return this.totalPages;
    }

    @Override
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public Integer getPageNumber() {
        return this.pageNumber;
    }

    @Override
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public List<T> getPagePayload() {
        return this.pagePayload;
    }

    @Override
    public void setPagePayload(List<T> pagePayload) {
        this.pagePayload = pagePayload;
    }
}
