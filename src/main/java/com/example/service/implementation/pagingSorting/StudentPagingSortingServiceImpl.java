package com.example.service.implementation.pagingSorting;

import com.example.dto.StudentDto;
import com.example.model.mapper.StudentMapper;
import com.example.repository.pagingSorting.StudentPagingAndSortingRepository;
import com.example.service.pagingSorting.StudentPagingSortingService;
import com.example.utils.SortingPagingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class implementation for PagingAndSorting Student Service.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class StudentPagingSortingServiceImpl implements StudentPagingSortingService {

    private final StudentPagingAndSortingRepository pagingAndSortingRepository;

    private final StudentMapper studentMapper;

    private final SortingPagingUtils sortingPagingUtils;

    @Override
    public Page<StudentDto> findPaginatedSortedStudents
            (final String studentName, final int page, final int size, final String[] sort){
        //Evaluate if we should sort by two fields.
        List<Sort.Order> orders = sortingPagingUtils.getSortOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Page<StudentDto> studentPage;
        List<StudentDto> studentDtos;
        if(studentName == null) {
            studentDtos = pagingAndSortingRepository
                    .findAll(pageable)
                    .stream()
                    .map(student -> studentMapper.studentToDto(student))
                    .collect(Collectors.toList());

        }
        else {
            studentDtos = pagingAndSortingRepository
                    .findByStudentNameContaining(studentName, pageable)
                    .stream()
                    .map(student -> studentMapper.studentToDto(student))
                    .collect(Collectors.toList());

        }
        studentPage = new PageImpl<>(studentDtos);
        return studentPage;
    }

}
