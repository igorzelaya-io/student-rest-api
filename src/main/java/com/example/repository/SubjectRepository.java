package com.example.repository;

import com.example.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {

    /**
     * Find Subject by its name.
     * @param subjectName String
     * @return Optional Subject
     */
    Optional<Subject> findSubjectBySubjectName(String subjectName);
    
}
