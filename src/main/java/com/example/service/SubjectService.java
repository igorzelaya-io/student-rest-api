package com.example.service;
import com.example.exception.SubjectNotFoundException;
import com.example.model.Subject;
import com.example.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    void saveSubject(Subject subject){
        this.subjectRepository.save(subject);
    }

    boolean existsSubject(String subjectId) {
        return this.subjectRepository.existsById(subjectId);
    }

    Subject findSubjectById(String subjectId){
        return this.subjectRepository.findById(subjectId)
            .orElseThrow(() -> SubjectNotFoundException
                    .buildSubjectNotFoundExceptionForId(subjectId));
    }

    Subject findSubjectByName(String subjectName){
        return this.subjectRepository.findSubjectBySubjectName(subjectName)
                .orElseThrow(() -> SubjectNotFoundException
                            .buildSubjectNotFoundExceptionForField("subjectName", subjectName));
    }

    void deleteSubject(String subjectId){
        this.subjectRepository.deleteById(subjectId);
    }

}
