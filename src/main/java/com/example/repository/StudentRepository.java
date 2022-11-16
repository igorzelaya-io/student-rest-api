package com.example.repository;

import java.util.Optional;

import com.example.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for Student entity.
 * @author Igor A. Zelaya
 * @version 1.0.0
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

	/**
	 * Find Student by its name and given status.
	 * @param studentId StudentId
	 * @return
	 */
	@Query("SELECT s FROM Student s " +
			"WHERE s.studentId = :studentId " +
			"AND s.studentStatus = 1")
	Optional<Student> findActiveStudentById(@Param("studentId") final String studentId);

	/**
	 * Find Student by its name and given status.
	 * @param studentName String
	 * @return Optional Student
	 */
	@Query("SELECT s FROM Student s " +
			"WHERE s.studentStatus = 1 " +
			"AND s.studentName LIKE %:studentName%")
	Optional<Student> findByNameAndStatusContaining(@Param("studentName") final String studentName);

	/**
	 * Find Paginated students by name.
	 * @param studentName
	 * @param pageable
	 * @return
	 */
	Page<Student> findByStudentNameContaining(String studentName, Pageable pageable);
	
}
