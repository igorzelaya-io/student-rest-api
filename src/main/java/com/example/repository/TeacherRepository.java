package com.example.repository;

import java.util.Optional;

import com.example.model.Subject;
import com.example.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for Teacher entity.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String>{

	/**
	 * Find a teacher by its name.
	 * @param teacherName String
	 * @return Optional Teacher
	 */
	Optional<Teacher> findByTeacherName(String teacherName);

	/**
	 * Find Paginated teachers by name.
	 * @param teacherName
	 * @param pageable
	 * @return
	 */
	Page<Teacher> findByTeacherNameContaining(String teacherName, Pageable pageable);

	/**
	 * Find Paginated List of Subjects a teacher teaches.
	 * @param teacherId
	 * @param pageable
	 * @return
	 */
	@Query("SELECT su FROM Subject su JOIN su.teacher t WHERE t.teacherId=:teacherId")
	Page<Subject> findTeacherSubjects(@Param("teacherId") String teacherId, Pageable pageable);
}
