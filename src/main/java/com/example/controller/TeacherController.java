package com.example.controller;

import com.example.dto.TeacherDto;
import com.example.model.Teacher;
import com.example.response.BaseResponse;
import com.example.response.Response;
import com.example.service.implementation.TeacherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * Controller for Teacher entity.
 * @author Igor A. Zelaya
 */
@RestController
@RequestMapping(path = "/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherServiceImpl teacherService;

    @GetMapping(value = "/{teacherId}")
    public ResponseEntity<? extends Response<TeacherDto>> findTeacherById(@PathVariable("teacherId")String teacherId) {
        TeacherDto retrievedTeacher = teacherService.findTeacherById(teacherId);
        BaseResponse<TeacherDto> teacherBaseResponse = new BaseResponse<>();
        return teacherBaseResponse.buildResponseEntity(HttpStatus.OK,
                "Teacher retrieved successfully", retrievedTeacher);
    }

    @PostMapping
    public ResponseEntity<? extends Response<TeacherDto>> saveTeacher(@RequestBody @Valid TeacherDto teacher) {
        this.teacherService.saveTeacher(teacher);
        BaseResponse<TeacherDto> teacherBaseResponse = new BaseResponse<>();
        return teacherBaseResponse.buildResponseEntity(HttpStatus.CREATED,
                "Teacher created successfully.", teacher);
    }

    @DeleteMapping(value = "/{teacherId}")
    public ResponseEntity<? extends Response<String>> deleteTeacher(@PathVariable("teacherId") String teacherId){
        BaseResponse<String> teacherBaseResponse = new BaseResponse<>();
        teacherService.deleteTeacherById(teacherId);
        return teacherBaseResponse.buildResponseEntity(HttpStatus.OK,
                new StringBuilder("Teacher with ID: ")
                        .append(teacherId)
                        .append( "was deleted.").toString(), teacherId);
    }

    @PutMapping(value = "/{teacherId}")
    public ResponseEntity<? extends Response<Teacher>> updateTeacher(@PathVariable("teacherId")String teacherId,
                                                                     @RequestBody Teacher teacher){
        BaseResponse<Teacher> teacherBaseResponse = new BaseResponse<>();
        //TODO Put in Service.
        return teacherBaseResponse.buildResponseEntity(HttpStatus.OK
                , "Teacher updated successfully", teacher);
    }
}
