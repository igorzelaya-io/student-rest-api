package com.example.service.pagingSorting;

import com.example.dto.StudentDto;
import com.example.model.Student;
import org.springframework.data.domain.Page;

/**
 * Paging and sorting service for Student entity.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
public interface StudentPagingSortingService {


    /**
     * Return a Page of sorted students.
     * @param studentName String name to query by.
     * @param page Page number to query by.
     * @param size Page size to query by.
     * @param sort Sort params to sort by.
     * @return Page StudentDto.
     */
    Page<StudentDto> findPaginatedSortedStudents(String studentName, int page, int size, String[] sort);

}