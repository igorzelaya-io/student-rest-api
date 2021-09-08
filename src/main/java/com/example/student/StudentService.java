package com.example.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

	private StudentRepository studentRepository;
	
	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	boolean studentExists(String studentId) {
		return studentRepository.existsById(studentId);
	}
	
	void saveStudent(Student student) {
		this.studentRepository.save(student);
	}
	
	Student findStudentById(String studentId) {
		return studentRepository.findById(studentId)
								.orElseThrow(() -> new RuntimeException("Student not found."));
	}
	
	Student findStudentByName(String studentName) {
		return studentRepository.findByStudentName(studentName)
								.orElseThrow(() -> new RuntimeException("Student not found."));
	}
	
	List<Student> findAllStudents(){
		return studentRepository.findAll();
	}
	
}
