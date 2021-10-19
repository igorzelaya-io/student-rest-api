package com.example.repository.pagingSorting;

import com.example.exception.StudentNotFoundException;
import com.example.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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


    /**
     * Finds Sorted List of all Students in which given name exists
     * @param studentName String
     * @param sort Sort
     * @return List Student
     */
    List<Student> findByStudentNameContaining(String studentName, Sort sort);


}
