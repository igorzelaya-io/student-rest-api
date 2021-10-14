package com.example.repository;

import java.util.Optional;

import com.example.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String>{

	Optional<Teacher> findByTeacherName(String teacherName);
	
}
