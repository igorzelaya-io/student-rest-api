package com.example.controller;

import javax.validation.Valid;

import com.example.dto.StudentDto;
import com.example.model.Student;
import com.example.response.BaseResponse;
import com.example.response.Response;
import com.example.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

/**
 * Controller for Student entity operations.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
@RestController
@RequestMapping(path = "/api/v1/students", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StudentController {

	private final StudentService studentService;

	/**
	 * Handler method for fetching a single Student by its ID.
	 * @param studentId String
	 * @return ResponseEntity Student
	 */
	@GetMapping(value = "/{studentId}")
	public ResponseEntity<? extends Student> findByStudentId(@PathVariable(value = "studentId")
																 final String studentId) {
		Student retrievedStudent = studentService.findStudentById(studentId);
		return new ResponseEntity<>(retrievedStudent, HttpStatus.OK);
	}

	/**
	 * Handler method for saving Validated given student.
	 * @param studentDto StudentDto
	 * @return ResponseEntity Response Student
	 */
	@PostMapping
	public ResponseEntity<? extends Response<StudentDto>> saveStudent(@RequestBody(required = true)
															 		@Valid StudentDto studentDto) {
		studentService.saveStudent(studentDto);
		BaseResponse<StudentDto> studentBaseResponse = new BaseResponse<>();
		return studentBaseResponse.buildResponseEntity(HttpStatus.CREATED, "Student saved successfully", studentDto);
	}

	/**
	 * Handler method for deleting a Student by its ID.
	 * @param studentId String
	 * @return Response
	 */
	@DeleteMapping(value = "/{studentId}")
	public ResponseEntity<? extends Response<Void>> deleteStudent(@PathVariable("studentId")final String studentId) {
		studentService.deleteStudentById(studentId);
		BaseResponse<Void> studentResponse = new BaseResponse<>();
		return studentResponse.buildResponseEntity(HttpStatus.OK, "Student deleted successfully", null);
	}
	
}
