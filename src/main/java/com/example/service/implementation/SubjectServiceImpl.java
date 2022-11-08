package com.example.service.implementation;
import com.example.dto.SubjectDto;
import com.example.exception.SubjectNotFoundException;
import com.example.model.Subject;
import com.example.model.mapper.SubjectMapper;
import com.example.model.status.ModelStatus;
import com.example.repository.SubjectRepository;
import com.example.service.SubjectService;
import com.example.utils.SortingPagingUtils;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

/**
 * Service class for Subject class
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectMapper subjectMapper;

    private final SubjectRepository subjectRepository;

    private final SortingPagingUtils sortingPagingUtils;

    /**
     * Optional constructor for SubjectService.
     * @param subjectRepository
     * @param sortingPagingUtils
     */
    public SubjectServiceImpl(SubjectRepository subjectRepository, SortingPagingUtils sortingPagingUtils){
        this.subjectMapper = Mappers.getMapper(SubjectMapper.class);
        this.subjectRepository = subjectRepository;
        this.sortingPagingUtils = sortingPagingUtils;
    }

    @Override
    public Page<SubjectDto> findBySubjectNameContaining
            (final String subjectName, final int page, final int size, final String[] sort) {
        List<Sort.Order> orders = sortingPagingUtils.getSortOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        List<SubjectDto> subjects;
        if(Objects.isNull(subjectName)){
            subjects = subjectMapper
                    .subjectsToDtos(subjectRepository.findAll(pageable).toList());
        }
        else {
            subjects = subjectMapper
                    .subjectsToDtos(subjectRepository
                            .findBySubjectNameContaining(subjectName, pageable).toList());
        }
        return new PageImpl<>(subjects);
    }

    @Override
    public SubjectDto saveSubject(SubjectDto subjectDto){
        Subject subject = Subject
                .buildFromDto(subjectMapper.dtoToSubject(subjectDto));
        subjectRepository.save(subject);
        return subjectMapper.toSubjectDto(subject);

    }

    @Override
    public SubjectDto findSubjectById(final String subjectId, final int statusCode){
        Subject subject = subjectRepository.findByIdAndStatus(subjectId, statusCode)
                .orElseThrow(() -> SubjectNotFoundException
                        .buildExceptionForId(subjectId));
        return subjectMapper
                .toSubjectDto(subject);
    }

    @Override
    public SubjectDto findSubjectByName(final String subjectName, final int statusCode) {
        Subject subject = subjectRepository.findBySubjectNameContainingLike(subjectName, 0)
                .orElseThrow(() -> SubjectNotFoundException
                        .buildExceptionForField("subjectName", subjectName));
        return subjectMapper
                .toSubjectDto(subject);
    }

    @Override
    public void deleteSubjectById(final String subjectId){
        Subject subject = subjectMapper
                .dtoToSubject(findSubjectById(
                        subjectId, ModelStatus.ACTIVE.getStatusCode()));
        subject.setSubjectStatus(ModelStatus.INACTIVE);
        subjectRepository.save(subject);
    }

    @Override
    public void updateSubject(Subject subject){
        subjectRepository.save(subject);
    }

    @Override
    public boolean subjectExists(String subjectName) {
        return subjectRepository.existsBySubjectNameIgnoreCase(subjectName);
    }

}
