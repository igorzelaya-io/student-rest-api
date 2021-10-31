package com.example.controller;

import javax.validation.Valid;

import com.example.dto.StudentDto;
import com.example.dto.pageable.PageResponse;
import com.example.dto.pageable.PageResponseDto;
import com.example.model.Student;
import com.example.response.BaseResponse;
import com.example.response.Response;
import com.example.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * Controller for Student entity operations.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
@RestController
@RequestMapping(path = "/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

	private final StudentService studentService;

	/**
	 * Get Paginated sorted students with given criteria.
	 * @param studentName String studentName
	 * @param page Page number
	 * @param size page size
	 * @param sort Sort params
	 * @return ResponseEntity PageResponse StudentDto
	 */
	@GetMapping(params = {"studentName", "page", "size", "sort"})
	public ResponseEntity<? extends PageResponse<StudentDto>> getStudents(
			@RequestParam(required = false)String studentName,
			@RequestParam(defaultValue = "0")int page,
			@RequestParam(defaultValue = "5")int size,
			@RequestParam(defaultValue = "studentId, desc") String[] sort) {

		Page<StudentDto> studentPage = studentService
				.findPaginatedSortedStudents(studentName, page, size, sort);

		PageResponseDto<StudentDto> pageResponseDto = new PageResponseDto<>();
		return pageResponseDto.buildResponseEntity(studentPage.getSize(), studentPage.getNumberOfElements(),
				studentPage.getTotalPages(), studentPage.getNumber(), studentPage.getContent());
	}

	/**
	 * Handler method for saving Validated given student.
	 * @param studentDto StudentDto
	 * @return ResponseEntity Response Student
	 */
	@PostMapping
	public ResponseEntity<? extends Response<StudentDto>> saveStudent(@RequestBody @Valid StudentDto studentDto) {
		StudentDto savedStudent = studentService.saveStudent(studentDto);
		BaseResponse<StudentDto> studentBaseResponse = new BaseResponse<>();
		return studentBaseResponse
				.buildResponseEntity(HttpStatus.CREATED, "Student saved successfully", savedStudent);
	}

	/**
	 * Handler method for fetching a single Student by its ID.
	 * @param studentId String
	 * @return ResponseEntity Student
	 */
	@GetMapping(value = "/{studentId}")
	public ResponseEntity<? extends StudentDto> findByStudentId(@PathVariable final String studentId) {
		StudentDto retrievedStudent = studentService.findStudentById(studentId);
		return new ResponseEntity<>(retrievedStudent, HttpStatus.OK);
	}

	/**
	 * Handler method for fetching a single Student by its name.
	 * @param studentName String
	 * @return ResponseEntity Student
	 */
	@GetMapping(params = "studentName")
	public ResponseEntity<? extends StudentDto> findStudentByName(@RequestParam("studentName") final String studentName){
		StudentDto retrievedStudent = studentService.findStudentByName(studentName);
		return new ResponseEntity<>(retrievedStudent, HttpStatus.OK);
	}

	/**
	 * Handler method for deleting a Student by its ID.
	 * @param studentId String
	 * @return Response null
	 */
	@DeleteMapping(value = "/{studentId}")
	public ResponseEntity<? extends Response<Void>> deleteStudent(@PathVariable final String studentId) {
		studentService.deleteStudentById(studentId);
		BaseResponse<Void> studentResponse = new BaseResponse<>();
		return studentResponse
				.buildResponseEntity(HttpStatus.OK, "Student deleted successfully", null);
	}
	
}
