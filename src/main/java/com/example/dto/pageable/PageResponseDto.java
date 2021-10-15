package com.example.dto.pageable;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class PageResponseDto<T> {

    private int pageSize;

    private int  numberOfElements;

    private int totalPages;

    private int pageNumber;

    private List<T> pagePayload;

}
