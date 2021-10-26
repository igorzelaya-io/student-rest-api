package com.example.service.implementation.pagingSorting;

import com.example.dto.TeacherDto;
import com.example.model.mapper.TeacherMapper;
import com.example.repository.pagingSorting.TeacherPagingAndSortingRepository;
import com.example.service.pagingSorting.TeacherPagingSortingService;
import com.example.utils.SortingPagingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for Teacher Paging and Sorting service.
 * @author Igor A. Zelaya
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class TeacherPagingSortingServiceImpl implements TeacherPagingSortingService {

    private final TeacherMapper teacherMapper;

    private final TeacherPagingAndSortingRepository teacherPagingAndSortingRepository;

    @Autowired
    private SortingPagingUtils sortingPagingUtils;

    @Override
    public Page<TeacherDto> findPaginatedSortedTeachers(String teacherName, int page, int size, String[] sort) {
        //Evaluate if we should sort by two fields.
        List<Sort.Order> orders = sortingPagingUtils.getSortOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Page<TeacherDto> teacherPage;
        List<TeacherDto> teacherDtoList;
        if (teacherName != null) {
            teacherDtoList = teacherPagingAndSortingRepository
                    .findByTeacherNameContaining(teacherName, pageable)
                    .stream()
                    .map(teacher -> teacherMapper.toTeacherDto(teacher))
                    .collect(Collectors.toList());
        } else {
            teacherDtoList = teacherPagingAndSortingRepository
                    .findAll(pageable)
                    .stream()
                    .map(teacher -> teacherMapper.toTeacherDto(teacher))
                    .collect(Collectors.toList());
        }
        teacherPage = new PageImpl<>(teacherDtoList);
        return teacherPage;
    }

}
