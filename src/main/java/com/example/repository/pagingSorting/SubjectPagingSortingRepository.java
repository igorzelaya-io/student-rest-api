package com.example.repository.pagingSorting;

import com.example.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Subject sorting and pagination.
 * @author Igor A. Zelaya
 */
@Repository
public interface SubjectPagingSortingRepository extends PagingAndSortingRepository<Subject, String> {

    /**
     * Find Subject sorted page by given name.
     * @param subjectName
     * @param pageable
     * @return
     */
    Page<Subject> findBySubjectNameContaining(String subjectName, Pageable pageable);

}
