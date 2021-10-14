package com.example.repository;

import java.util.Optional;

import com.example.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>{
	
	Optional<Student> findByStudentName(String studentName);
	
}
