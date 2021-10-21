package com.example.service.implementation;

import com.example.dto.StudentDto;
import com.example.dto.pageable.PageResponseDto;
import com.example.model.Student;
import com.example.repository.pagingSorting.StudentPagingAndSortingRepository;
import com.example.service.pagingSorting.StudentPagingSortingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class implementation for PagingAndSorting Student Service.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class StudentPagingSortingServiceImpl implements StudentPagingSortingService {

    private final StudentPagingAndSortingRepository pagingAndSortingRepository;

    @Override
    public Page<StudentDto> findPaginatedSortedStudents(String studentName, int page, int size, String[] sort){
        //Evaluate if we should sort by two fields.
        List<Sort.Order> orders = new ArrayList<>();
        if(sort[0].contains(",")){
            for(String sortOrder: sort){

            }
        }
        return null;
    }

    /**
     * Return a List of Order to sort by.
     * @param sort
     * @return
     */
    private List<Sort.Order> getSortOrders(String[] sort){
        List<Sort.Order> sortingOrders = new ArrayList<>();
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
