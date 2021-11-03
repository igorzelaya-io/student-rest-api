package com.example.repository;

import java.util.Optional;

import com.example.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
	
}
