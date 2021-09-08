package com.example.student;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class StudentController {

	private StudentService studentService;
	
	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	@GetMapping(value = "/students/{studentId}")
	public ResponseEntity<? extends Student> findByStudentId(@PathVariable(value = "studentId", required = true) final String studentId){
		Student retrievedStudent = studentService.findStudentById(studentId);
		return new ResponseEntity<Student>(retrievedStudent, HttpStatus.OK);
	}
	
	@GetMapping(value = "/students")
	public ResponseEntity<List<Student>> findAllStudents(){
		List<Student> studentList = studentService.findAllStudents();
		if(studentList.isEmpty()) {
			return new ResponseEntity<>(studentList, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Student>>(studentList, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/students")
	public ResponseEntity<? extends Student> saveStudent(@RequestBody(required = true)@Valid Student student){
		studentService.saveStudent(student);
		return new ResponseEntity<>(student, HttpStatus.CREATED);
	}
	
	
}
