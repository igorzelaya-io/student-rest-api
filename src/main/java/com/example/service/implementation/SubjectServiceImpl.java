package com.example.service.implementation;
import com.example.dto.SubjectDto;
import com.example.exception.SubjectNotFoundException;
import com.example.model.Subject;
import com.example.model.mapper.SubjectMapper;
import com.example.model.status.ModelStatus;
import com.example.repository.SubjectRepository;
import com.example.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for Subject class
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private SubjectMapper subjectMapper;

    private SubjectRepository subjectRepository;

    @Override
    public void saveSubject(SubjectDto subjectDto){
        Subject subject = Subject.buildFromDto(subjectMapper.dtoToSubject(subjectDto));
        this.subjectRepository.save(subject);
    }

    @Override
    public Subject findSubjectById(String subjectId){
        if(existsSubject(subjectId)){
            return this.subjectRepository.findById(subjectId)
                .orElseThrow(() -> SubjectNotFoundException
                        .buildSubjectNotFoundExceptionForId(subjectId));
        }
        throw SubjectNotFoundException.buildSubjectNotFoundExceptionForId(subjectId);
    }

    @Override
    public Subject findSubjectByName(String subjectName){
        Subject subject = this.subjectRepository.findSubjectBySubjectName(subjectName)
                .orElseThrow(() -> SubjectNotFoundException
                        .buildSubjectNotFoundExceptionForField("subjectName", subjectName));
        if(subject.getSubjectStatus().equals(ModelStatus.ACTIVE)){
            return subject;
        }
        throw SubjectNotFoundException.buildSubjectNotFoundExceptionForField("subjectName", subjectName);
    }

    @Override
    public void deleteSubjectById(String subjectId){
        Subject subject = findSubjectById(subjectId);
        subject.setSubjectStatus(ModelStatus.INACTIVE);
        subjectRepository.save(subject);
    }

    /**
     * Evaluate if Subject exists in DB AND status is ACTIVE.
     * @param subjectId
     * @return
     */
    private boolean existsSubject(String subjectId) {
        if(subjectRepository.existsById(subjectId)){
            Subject subject = subjectRepository.findById(subjectId).get();
            return subject.getSubjectStatus().getStatusCode() == 0;
        }
        return false;
    }
}
