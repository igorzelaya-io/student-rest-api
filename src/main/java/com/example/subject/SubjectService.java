package com.example.subject;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
    }

    void saveSubject(Subject subject){
        this.subjectRepository.save(subject);
    }

    boolean existsSubject(String subjectId) {
        return this.subjectRepository.existsById(subjectId);
    }

    Subject findSubjectById(String subjectId){
        return this.subjectRepository.findById(subjectId)
            .orElseThrow(() ->
                    SubjectNotFoundException
                            .buildSubjectNotFoundExceptionForId(subjectId));
    }

    Subject findSubjectByName(String subjectName){
        return this.subjectRepository.findSubjectBySubjectName(subjectName)
                .orElseThrow(() ->
                    SubjectNotFoundException
                            .buildSubjectNotFoundExceptionForField("subjectName", subjectName));
    }

}
