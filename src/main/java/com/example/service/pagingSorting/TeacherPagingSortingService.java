package com.example.service.pagingSorting;


import com.example.dto.TeacherDto;
import org.springframework.data.domain.Page;

/**
 * Teacher Repository
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
public interface TeacherPagingSortingService {

    /**
     * Return a Page of sorted teachers by given data.
     * @param teacherName String
     * @param page int
     * @param size int
     * @param sort Sort parameters.
     * @return Page TeacherDto
     */
    Page<TeacherDto> findPaginatedSortedTeachers
    (final String teacherName, final int page, final int size, final String[] sort);

}
