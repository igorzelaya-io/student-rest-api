package com.example.repository;

import com.example.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, String> {

    Optional<Subject>  findSubjectBySubjectName(String subjectName);
    
}
