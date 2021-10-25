package com.example.service.implementation.pagingSorting;

import com.example.dto.SubjectDto;
import com.example.model.mapper.SubjectMapper;
import com.example.repository.pagingSorting.SubjectPagingSortingRepository;
import com.example.service.pagingSorting.SubjectPagingSortingService;
import com.example.utils.SortingPagingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectPagingSortingServiceImpl implements SubjectPagingSortingService {

    private final SortingPagingUtils sortingPagingUtils;

    private final SubjectPagingSortingRepository subjectPagingSortingRepository;

    private final SubjectMapper subjectMapper;

    @Override
    public Page<SubjectDto> findBySubjectNameContaining
            (final String subjectName, final int page, final int size, final String[] sort) {
        //Evaluate if we should sort by two fields
        List<Sort.Order> orders = sortingPagingUtils.getSortOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Page<SubjectDto> subjectPage;
        List<SubjectDto> subjectDtos;
        if(subjectName == null){
            subjectDtos = subjectPagingSortingRepository
                    .findAll(pageable)
                    .stream()
                    .map(subject -> subjectMapper.toSubjectDto(subject))
                    .collect(Collectors.toList());
        } else {
            subjectDtos = subjectPagingSortingRepository
                    .findBySubjectNameContaining(subjectName, pageable)
                    .stream()
                    .map(subject -> subjectMapper.toSubjectDto(subject))
                    .collect(Collectors.toList());
        }
        subjectPage = new PageImpl<>(subjectDtos);
        return subjectPage;
    }
}
