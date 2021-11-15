package com.example.controller;

import com.example.dto.SubjectDto;
import com.example.dto.TeacherDto;
import com.example.dto.openapi.PageResponseTeacherDto;
import com.example.dto.openapi.ResponseTeacherDto;
import com.example.dto.pageable.PageResponse;
import com.example.dto.pageable.PageResponseDto;
import com.example.response.BaseResponse;
import com.example.response.Response;
import com.example.response.error.ErrorResponse;
import com.example.service.TeacherService;
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
import javax.validation.Valid;

/**
 * Controller for Teacher entity.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
@RestController
@RequestMapping(path = "/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    /**
     * Get sorted and paginated list of users.
     * @param teacherName String
     * @param page page number String
     * @param size page size string
     * @param sort Sorting params
     * @return ResponseEntity PageResponse TeacherDto
     */
    @Operation(summary = "Get a list of paginated/sorted teachers")
    @ApiResponse(responseCode = "200", description = "List of teachers retrieved successfully"
        , content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
        , schema = @Schema(implementation = PageResponseTeacherDto.class))})
    @GetMapping(params = {"teacherName", "page", "size", "sort"})
    public ResponseEntity<? extends PageResponse<TeacherDto>> getTeachers(
            @RequestParam(required = false) final String teacherName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5")int size,
            @RequestParam(defaultValue = "teacherId, desc")String[] sort){

        Page<TeacherDto> teacherPage = teacherService.findPaginatedSortedTeachers(teacherName,page, size, sort);
        PageResponseDto<TeacherDto> teacherBaseResponse = new PageResponseDto<>();
        return teacherBaseResponse
                .buildResponseEntity(teacherPage.getSize(), teacherPage.getNumberOfElements(),
                        teacherPage.getTotalPages(), teacherPage.getNumber(), teacherPage.getContent());
    }


    /**
     * Handler method for fetching a single Teacher by its ID.
     * @param teacherId String
     * @return ResponseEntity TeacherDto
     */
    @Operation(summary = "Find teacher by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",  description = "Teacher found successfully."
            , content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(implementation = ResponseTeacherDto.class))}),
            @ApiResponse(responseCode = "404", description = "Teacher not found."
            , content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping(value = "/{teacherId}")
    public ResponseEntity<? extends Response<TeacherDto>> findTeacherById(@PathVariable final String teacherId) {
        TeacherDto retrievedTeacher = teacherService.findTeacherById(teacherId);
        BaseResponse<TeacherDto> teacherBaseResponse = new BaseResponse<>();
        return teacherBaseResponse.buildResponseEntity(HttpStatus.OK,
                "Teacher retrieved successfully", retrievedTeacher);
    }

    /**
     * Handler method for fetching a single Teacher by its name.
     * @param teacherName String
     * @return ResponseEntity TeacherDto
     */
    @Operation(summary = "Find teacher by name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher found successfully."
            , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(implementation = ResponseTeacherDto.class))),
            @ApiResponse(responseCode = "404", description = "Teacher was not found"
            , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(implementation = ResponseTeacherDto.class)))
    })
    @GetMapping(params = "teacherName")
    public ResponseEntity<? extends Response<TeacherDto>> findTeacherByName(@RequestParam final String teacherName){
        TeacherDto retrievedTeacher = teacherService.findTeacherByName(teacherName);
        BaseResponse<TeacherDto> teacherBaseResponse = new BaseResponse<>();
        return teacherBaseResponse
                .buildResponseEntity(HttpStatus.OK, "Teacher retrieved successfully.", retrievedTeacher);
    }


    @Operation(summary = "Save given teacher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Teacher saved successfully"
            , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(implementation = ResponseTeacherDto.class))),
            @ApiResponse(responseCode = "400", description = "Given teacher is invalid"
            , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<? extends Response<TeacherDto>> saveTeacher(@RequestBody @Valid TeacherDto teacher) {
        this.teacherService.saveTeacher(teacher);
        BaseResponse<TeacherDto> teacherBaseResponse = new BaseResponse<>();
        return teacherBaseResponse.buildResponseEntity(HttpStatus.CREATED,
                "Teacher created successfully.", teacher);
    }

    @Operation(summary = "Add subject to teacher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subject added to teacher successfully"
            , content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Given subject is invalid"
            , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(path = "/{teacherId}/subjects")
    public ResponseEntity<? extends Response<String>> addSubjectToTeacher(@PathVariable("teacherId")String teacherId,
                                                                 @RequestBody @Valid SubjectDto subjectDto){
        teacherService.addSubjectToTeacher(teacherId, subjectDto);
        BaseResponse<String> teacherResponse = new BaseResponse<>();
        return teacherResponse
                .buildResponseEntity(HttpStatus.OK, "Subject added successfully to teacher", teacherId);
    }

    @DeleteMapping(path = "/{teacherId}/subjects/{subjectId}")
    public ResponseEntity<? extends Response<String>> removeSubjectFromTeacher(@PathVariable("teacherId")String teacherId,
                                                                               @PathVariable("subjectId")String subjectId){
        removeSubjectFromTeacher(teacherId, subjectId);
        BaseResponse<String> teacherResponse = new BaseResponse<>();
        return teacherResponse
                .buildResponseEntity(HttpStatus.OK, "Subject removed successfully", teacherId);
    }

    @Operation(summary = "Delete teacher by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher deleted successfully."
            , content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping(value = "/{teacherId}")
    public ResponseEntity<? extends Response<String>> deleteTeacher(@PathVariable("teacherId") final String teacherId){
        BaseResponse<String> teacherBaseResponse = new BaseResponse<>();
        teacherService.deleteTeacherById(teacherId);
        return teacherBaseResponse.buildResponseEntity(HttpStatus.OK,
                new StringBuilder("Teacher with ID: ")
                        .append(teacherId)
                        .append( "was deleted.").toString(), teacherId);
    }

    @Operation(description = "Update given teacher record given its ID.")
    @ApiResponse(responseCode = "200", description = "Record updated successfully"
            ,content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
    , schema = @Schema(implementation = ResponseTeacherDto.class)))
    @PutMapping(value = "/{teacherId}")
    public ResponseEntity<? extends Response<TeacherDto>> updateTeacher(@PathVariable("teacherId")final String teacherId,
                                                                     @RequestBody TeacherDto teacherDto){
        BaseResponse<TeacherDto> teacherBaseResponse = new BaseResponse<>();
        //TODO Put in Service.
        return teacherBaseResponse.buildResponseEntity(HttpStatus.OK
                , "Teacher updated successfully", teacherDto);
    }
}
