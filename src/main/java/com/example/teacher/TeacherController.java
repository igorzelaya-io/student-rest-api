package com.example.teacher;

import com.example.response.BaseResponse;
import com.example.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController(value = "/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping(value = "/{teacherId}")
    public ResponseEntity<? extends Response<Teacher>> findTeacherById(@PathVariable("teacherId")String teacherId) {
        Teacher retrievedTeacher = teacherService.findTeacherById(teacherId);
        BaseResponse<Teacher> teacherBaseResponse = new BaseResponse<>();
        return teacherBaseResponse.buildResponseEntity(HttpStatus.OK, "Teacher retrieved successfully", retrievedTeacher);
    }

    @PostMapping
    public ResponseEntity<? extends Response<Teacher>> saveTeacher(@RequestBody @Valid Teacher teacher) {
        this.teacherService.saveTeacher(teacher);
        BaseResponse<Teacher> teacherBaseResponse = new BaseResponse<>();
        return teacherBaseResponse.buildResponseEntity(HttpStatus.CREATED, "Teacher created successfully.", teacher);
    }

    @DeleteMapping(value = "/{teacherId}")
    public ResponseEntity<? extends Response<String>> deleteTeacher(@PathVariable("teacherId") String teacherId){
        BaseResponse<String> teacherBaseResponse = new BaseResponse<>();
        return teacherBaseResponse.buildResponseEntity(HttpStatus.OK, new StringBuilder("Teacher with ID: ").append(teacherId).append( "was deleted.").toString(), teacherId);
    }
}
