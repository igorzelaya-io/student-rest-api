package com.example.repository;

import java.util.Optional;

import com.example.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Student entity.
 * @author Igor A. Zelaya
 * @version 1.0.0
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String>{

	/**
	 * Find Student by its name.
	 * @param studentName String
	 * @return Optional Student
	 */
	Optional<Student> findByStudentName(String studentName);
	
}
