package com.example.subject;

import com.example.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
    }

    void saveSubject(Subject subject){
        this.subjectRepository.save(subject);
    }

    boolean existsSubject(String subjectId){
        return this.subjectRepository.existsById(subjectId);
    }

    Subject findSubjectById(String subjectId){
        return this.subjectRepository.findById(subjectId)
            .orElseThrow(() -> new ResourceNotFoundException(""));
    }

    Subject findSubjectByName(String subjectName){
        return this.subjectRepository.findSubjectBySubjectName(subjectName)
                .orElseThrow(() -> new ResourceNotFoundException(""));
    }

}
