package com.example.controller;


import com.example.dto.SubjectDto;
import com.example.response.BaseResponse;
import com.example.response.Response;
import com.example.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;





    /**
     * POST handler method for saving validated given Subject entity.
     * @param subjectDto SubjectDto
     * @return ResponseEntity SubjectDto
     */
    @PostMapping
    public ResponseEntity<? extends Response<SubjectDto>> saveSubject(@RequestBody
                                                                      @Valid SubjectDto subjectDto){
        subjectService.saveSubject(subjectDto);
        BaseResponse<SubjectDto> subjectDtoBaseResponse = new BaseResponse<>();
        return subjectDtoBaseResponse
                .buildResponseEntity(HttpStatus.CREATED, "Subject saved successfully", subjectDto);
    }

    /**
     * GET handler method for fetching a Subject by its ID.
     * @param subjectId String
     * @return ResponseEntity SubjectDto
     */
    @GetMapping(path = "/{subjectId}")
    public ResponseEntity<? extends Response<SubjectDto>> findSubjectById(@PathVariable String subjectId){
        SubjectDto subjectDto = subjectService.findSubjectById(subjectId);
        BaseResponse<SubjectDto> subjectDtoBaseResponse = new BaseResponse<>();
        return subjectDtoBaseResponse
                .buildResponseEntity(HttpStatus.OK, "Subject found",  subjectDto);
    }

    /**
     * DELETE handler method for deleting a Subject by its ID.
     * @param subjectId
     * @return
     */
    @DeleteMapping(path = "/{subjectId}")
    public ResponseEntity<? extends Response<Void>> deleteSubjectById(@PathVariable String subjectId){
        subjectService.deleteSubjectById(subjectId);
        BaseResponse<Void> subjectResponse = new BaseResponse<>();
        return subjectResponse
                .buildResponseEntity(HttpStatus.OK, "Subject deleted successfully.", null);
    }
}
