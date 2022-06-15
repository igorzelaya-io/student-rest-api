package com.example.repository;

import com.example.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Subject entity.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {

    /**
     * Find student by status and ID.
     * @param subjectId SubjectId
     * @param statusCode statusCode
     * @return Optional Subject
     */
    @Query("FROM Subject s WHERE s.subjectStatus = :status AND s.subjectId = :subjectId")
    Optional<Subject> findByIdAndStatus(@Param("subjectId") final String subjectId,
                                        @Param("status") final int statusCode);

    /**
     * Find Subject by its name.
     * @param subjectName String
     * @return Optional Subject
     */
    @Query("FROM Subject s WHERE s.subjectStatus = :statusCode AND LOWER(s.subjectName) LIKE LOWER(CONCAT('%', :subjectName,'%') )")
    Optional<Subject> findBySubjectNameContaining(@Param("subjectName") final String subjectName,
                                                  @Param("statusCode") final int statusCode);

    /**
     * Find Paginated subjects by name.
     * @param subjectName
     * @param pageable
     * @return
     */
    Page<Subject> findBySubjectNameContaining(String subjectName, Pageable pageable);

    /**
     * Validate if subject exists.
     * @param subjectName
     * @return
     */
    boolean existsBySubjectNameIgnoreCase(String subjectName);
    
}
