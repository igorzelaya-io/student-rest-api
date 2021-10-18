package com.example.service.implementation;
import com.example.dto.SubjectDto;
import com.example.exception.SubjectNotFoundException;
import com.example.model.Student;
import com.example.model.Subject;
import com.example.model.mapper.SubjectMapper;
import com.example.model.status.ModelStatus;
import com.example.repository.SubjectRepository;
import com.example.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Override
    public void saveSubject(SubjectDto subjectDto){
        Subject subject = Subject
                .buildFromDto(subjectMapper.dtoToSubject(subjectDto));
        this.subjectRepository.save(subject);
    }

    @Override
    public SubjectDto findSubjectById(final String subjectId){
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> SubjectNotFoundException
                        .buildSubjectNotFoundExceptionForId(subjectId));
        return subjectMapper
                .toSubjectDto(
                        isActiveSubject(subject, "subjectId", subjectId));
    }

    @Override
    public SubjectDto findSubjectByName(final String subjectName){
        Subject subject = subjectRepository.findSubjectBySubjectName(subjectName)
                .orElseThrow(() -> SubjectNotFoundException
                        .buildSubjectNotFoundExceptionForField("subjectName", subjectName));
        return subjectMapper
                .toSubjectDto(
                        isActiveSubject(subject, "subjectName", subjectName));
    }

    @Override
    public void deleteSubjectById(final String subjectId){
        Subject subject = subjectMapper.dtoToSubject(findSubjectById(subjectId));
        subject.setSubjectStatus(ModelStatus.INACTIVE);
        subjectRepository.save(subject);
    }

    /**
     * Return subject if status code is ACTIVE.
     * @param subject Subject
     * @param queryField String
     * @param fieldValue String
     * @return Subject
     * @throws SubjectNotFoundException ex
     */
    private Subject isActiveSubject(Subject subject, String queryField, String fieldValue){
        if(subject.getSubjectStatus().getStatusCode() == 0){
            return subject;
        }
        throw SubjectNotFoundException
                .buildSubjectNotFoundExceptionForField(queryField, fieldValue);
    }

}