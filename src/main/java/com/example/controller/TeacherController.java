package com.example.controller;

import com.example.dto.TeacherDto;
import com.example.dto.pageable.PageResponse;
import com.example.dto.pageable.PageResponseDto;
import com.example.model.Teacher;
import com.example.response.BaseResponse;
import com.example.response.Response;
import com.example.service.implementation.TeacherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

    private final TeacherServiceImpl teacherService;

    /**
     * Get sorted and paginated list of users.
     * @param teacherName String
     * @param page page number String
     * @param size page size string
     * @param sort Sorting params
     * @return ResponseEntity PageResponse TeacherDto
     */
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
    @GetMapping(params = "teacherName")
    public ResponseEntity<? extends Response<TeacherDto>> findTeacherByName(@RequestParam final String teacherName){
        TeacherDto retrievedTeacher = teacherService.findTeacherByName(teacherName);
        BaseResponse<TeacherDto> teacherBaseResponse = new BaseResponse<>();
        return teacherBaseResponse
                .buildResponseEntity(HttpStatus.OK, "Teacher retrieved successfully.", retrievedTeacher);
    }

    @PostMapping
    public ResponseEntity<? extends Response<TeacherDto>> saveTeacher(@RequestBody @Valid TeacherDto teacher) {
        this.teacherService.saveTeacher(teacher);
        BaseResponse<TeacherDto> teacherBaseResponse = new BaseResponse<>();
        return teacherBaseResponse.buildResponseEntity(HttpStatus.CREATED,
                "Teacher created successfully.", teacher);
    }

    @DeleteMapping(value = "/{teacherId}")
    public ResponseEntity<? extends Response<String>> deleteTeacher(@PathVariable("teacherId") final String teacherId){
        BaseResponse<String> teacherBaseResponse = new BaseResponse<>();
        teacherService.deleteTeacherById(teacherId);
        return teacherBaseResponse.buildResponseEntity(HttpStatus.OK,
                new StringBuilder("Teacher with ID: ")
                        .append(teacherId)
                        .append( "was deleted.").toString(), teacherId);
    }

    @PutMapping(value = "/{teacherId}")
    public ResponseEntity<? extends Response<Teacher>> updateTeacher(@PathVariable("teacherId")final String teacherId,
                                                                     @RequestBody Teacher teacher){
        BaseResponse<Teacher> teacherBaseResponse = new BaseResponse<>();
        //TODO Put in Service.
        return teacherBaseResponse.buildResponseEntity(HttpStatus.OK
                , "Teacher updated successfully", teacher);
    }
}
