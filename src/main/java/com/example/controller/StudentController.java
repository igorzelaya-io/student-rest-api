package com.example.controller;

import javax.validation.Valid;
import com.example.dto.StudentDto;
import com.example.dto.SubjectDto;
import com.example.dto.openapi.PageResponseStudentDto;
import com.example.dto.openapi.ResponseStudentDto;
import com.example.dto.pageable.PageResponse;
import com.example.dto.pageable.PageResponseDto;
import com.example.response.BaseResponse;
import com.example.response.Response;
import com.example.response.error.ErrorResponse;
import com.example.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	@Operation(summary = "Get a list of paginated/sorted students", operationId = "getStudents")
	@ApiResponse(responseCode = "200", description = "List of students retrieved successfully."
		,  content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
		, schema = @Schema(implementation = PageResponseStudentDto.class))})
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
	@Operation(summary = "Save given student.", operationId = "saveStudent")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201",  description = "Student saved successfully"
			,content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
			, schema = @Schema(implementation = ResponseStudentDto.class))})
			, @ApiResponse(responseCode = "400", description = "Given Student is invalid"
			, content = { @Content(schema = @Schema(implementation = ErrorResponse.class))})}
	)
	@PostMapping
	public ResponseEntity<? extends Response<StudentDto>> saveStudent(
			@RequestBody @Valid StudentDto studentDto) {

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
	@Operation(description = "Find a student by its ID.", operationId = "findStudentById")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Student retrieved successfully"
			, content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
			, schema = @Schema(implementation =  StudentDto.class))})
			, @ApiResponse(responseCode = "404", description = "Student not found"
	, content = { @Content(schema = @Schema(implementation = ErrorResponse.class))})
	})
	@GetMapping(value = "/{studentId}")
	public ResponseEntity<? extends StudentDto> findByStudentId(
			@PathVariable final String studentId) {

		StudentDto retrievedStudent = studentService.findStudentById(studentId);
		return new ResponseEntity<>(retrievedStudent, HttpStatus.OK);
	}

	/**
	 * Handler method for fetching a single Student by its name.
	 * @param studentName String
	 * @return ResponseEntity Student
	 */
	@Operation(description = "Find a student by its name.", operationId = "findStudentByName")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Student retrieved successfully"
					, content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
					, schema = @Schema(implementation =  StudentDto.class))})
			, @ApiResponse(responseCode = "404", description = "Student not found"
			, content = { @Content(schema = @Schema(implementation = ErrorResponse.class))})
	})
	@GetMapping(params = "studentName")
	public ResponseEntity<? extends StudentDto> findStudentByName(
			@RequestParam("studentName") final String studentName){

		StudentDto retrievedStudent = studentService.findStudentByName(studentName);
		return new ResponseEntity<>(retrievedStudent, HttpStatus.OK);
	}

	/**
	 * Handler method for deleting a Student by its ID.
	 * @param studentId String
	 * @return Response null
	 */
	@Operation(description = "Delete a student by its ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Student deleted successfully."
					, content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
					, schema = @Schema(implementation =  String.class))})
			, @ApiResponse(responseCode = "404", description = "Student not found"
			, content = { @Content(schema = @Schema(implementation = ErrorResponse.class))})
	})
	@DeleteMapping(value = "/{studentId}")
	public ResponseEntity<? extends Response<String>> deleteStudent(@PathVariable final String studentId) {
		studentService.deleteStudentById(studentId);
		BaseResponse<String> studentResponse = new BaseResponse<>();
		return studentResponse
				.buildResponseEntity(HttpStatus.OK, new StringBuilder("Student with ID: ")
						.append(studentId)
						.append(" was deleted.").toString(), studentId);
	}

	@PostMapping(value = "/{studentId}/subjects/{subjectId}")
	public ResponseEntity<? extends Response<String>> addSubjectToStudent(@PathVariable("studentId")final String studentId,
																		  @PathVariable("subjectId")final String subjectId){
		BaseResponse<String> response = new BaseResponse<>();
		studentService.addSubjectToStudent(studentId, subjectId, null);
		return response.buildResponseEntity(HttpStatus.OK, "Subject added successfully to student.", null);
	}

	@PostMapping(value = "/{studentId}/subjects")
	public ResponseEntity<? extends Response<String>> addSubjectToStudent(@PathVariable("studentId")final String studentId,
																		  @RequestBody @Valid SubjectDto subjectDto){
		BaseResponse<String> response = new BaseResponse<>();
		studentService.addSubjectToStudent(studentId, null, subjectDto);
		return response.buildResponseEntity(HttpStatus.OK, "Subject added successfully to student.", studentId);
	}
}
