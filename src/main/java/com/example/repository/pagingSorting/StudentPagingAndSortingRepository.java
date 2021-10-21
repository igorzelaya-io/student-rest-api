package com.example.repository.pagingSorting;

import com.example.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Paging and sorting repository for Student entity.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
@Repository
public interface StudentPagingAndSortingRepository
        extends PagingAndSortingRepository<Student, String> {

    /**
     * Finds Page of all Students in which given name exists.
     * @param studentName String
     * @param pageable Pageable
     * @return Page Student
     */
    Page<Student> findByStudentNameContaining(String studentName, Pageable pageable);

}
