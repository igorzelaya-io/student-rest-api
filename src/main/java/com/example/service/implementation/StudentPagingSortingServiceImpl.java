package com.example.service.implementation;

import com.example.dto.StudentDto;
import com.example.model.mapper.StudentMapper;
import com.example.repository.pagingSorting.StudentPagingAndSortingRepository;
import com.example.service.pagingSorting.StudentPagingSortingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public Page<StudentDto> findPaginatedSortedStudents(String studentName, int page, int size, String[] sort){
        //Evaluate if we should sort by two fields.
        List<Sort.Order> orders = getSortOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Page<StudentDto> studentPage;
        if(studentName == null){
            studentPage = new PageImpl<>(pagingAndSortingRepository.findAll(pageable)
                    .stream()
                    .map(student -> studentMapper.studentToDto(student))
                    .collect(Collectors.toList()));
            return studentPage;
        }
        studentPage = new PageImpl<>(
                            pagingAndSortingRepository
                            .findByStudentNameContaining(studentName, pageable)
                            .stream()
                            .map(student -> studentMapper.studentToDto(student))
                            .collect(Collectors.toList())
                           );
        return studentPage;
    }

    /**
     * Return a List of Order to sort by.
     * @param sort
     * @return
     */
    private List<Sort.Order> getSortOrders(String[] sort){
        List<Sort.Order> sortingOrders = new ArrayList<>();
        //Evaluate if we should sort by more than one field.
        if(sort[0].contains(",")){
            //sortOrder = "field, direction"
            for(String sortOrder: sort){
                String[] _sort = sortOrder.split(",");
                sortingOrders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        }
        else{
            sortingOrders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        return sortingOrders;
    }

    /**
     * Return sortDirection given string.
     * @param sortDirection
     * @return
     */
    private Sort.Direction getSortDirection(String sortDirection){
        if(sortDirection.equals("desc")){
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

}
