package com.example.service.pagingSorting;

import com.example.dto.SubjectDto;
import org.springframework.data.domain.Page;

/**
 * Paging and sorting service for Subject entity.
 * @author Igor A. Zelaya
 * @version 1.0.0
 */
public interface SubjectPagingSortingService {

    /**
     * Find sorted and paginated SubjectDto.
     * @param subjectName String
     * @param page page number
     * @param size page size
     * @param sort Sorting parameters
     * @return Page SubjectDto
     */
    Page<SubjectDto> findBySubjectNameContaining
            (final String subjectName, int page, int size, final String[] sort);
}
