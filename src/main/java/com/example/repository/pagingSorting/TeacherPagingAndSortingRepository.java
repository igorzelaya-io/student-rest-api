package com.example.repository.pagingSorting;

import com.example.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Teacher entity.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
@Repository
public interface TeacherPagingAndSortingRepository
        extends PagingAndSortingRepository<Teacher, String> {

    /**
     * Finds Page of all Teachers in which given name exists.
     * @param teacherName String
     * @param pageable Page
     * @return Page Teacher
     */
    Page<Teacher> findByTeacherNameContaining(String teacherName, Pageable pageable);

}
