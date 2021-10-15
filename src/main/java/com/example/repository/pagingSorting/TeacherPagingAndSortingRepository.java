package com.example.repository.pagingSorting;

import com.example.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeacherPagingAndSortingRepository
        extends PagingAndSortingRepository<Teacher, String> {

    Page<Teacher> findAll(Pageable pageable);

}
