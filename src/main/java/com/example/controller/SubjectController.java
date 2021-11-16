package com.example.controller;


import com.example.dto.SubjectDto;
import com.example.dto.pageable.PageResponse;
import com.example.dto.pageable.PageResponseDto;
import com.example.model.Subject;
import com.example.response.BaseResponse;
import com.example.response.Response;
import com.example.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for Subject entity.
 * @author Igor A. Zelaya
 * @version 1.0.0
 */
@RestController
@RequestMapping(path = "/api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    /**
     * Get sorted and paginated list of subjects.
     * @param subjectName Subject Name
     * @param page page number
     * @param size page size
     * @param sort sort parameters
     * @return PageResponse of SubjectDto
     */
    @GetMapping(params = {"subjectName", "page", "size", "sort"})
    public ResponseEntity<? extends PageResponse<SubjectDto>> getSubjects(@RequestParam(required = false)String subjectName,
                                                                          @RequestParam(defaultValue = "0")int page,
                                                                          @RequestParam(defaultValue = "3")int size,
                                                                          @RequestParam(defaultValue = "subjectId,desc")String[] sort){
        Page<SubjectDto> subjectDto = subjectService
                .findBySubjectNameContaining(subjectName, page, size, sort);

        PageResponseDto<SubjectDto> pageResponseDto = new PageResponseDto<>();
        return pageResponseDto.buildResponseEntity(subjectDto.getSize(), subjectDto.getNumberOfElements(),
                subjectDto.getTotalPages(), subjectDto.getNumber(), subjectDto.getContent());
    }


    /**
     * POST handler method for saving validated given Subject entity.
     * @param subjectDto SubjectDto
     * @return ResponseEntity SubjectDto
     */
    @PostMapping
    public ResponseEntity<? extends Response<SubjectDto>> saveSubject(@RequestBody
                                                                      @Valid SubjectDto subjectDto){
        SubjectDto savedSubject = subjectService.saveSubject(subjectDto);
        BaseResponse<SubjectDto> subjectDtoBaseResponse = new BaseResponse<>();
        return subjectDtoBaseResponse
                .buildResponseEntity(HttpStatus.CREATED, "Subject saved successfully", savedSubject);
    }

    /**
     * GET handler method for fetching a Subject by its ID.
     * @param subjectId String
     * @return ResponseEntity SubjectDto
     */
    @GetMapping(path = "/{subjectId}")
    public ResponseEntity<? extends Response<SubjectDto>> findSubjectById(@PathVariable String subjectId) {
        SubjectDto subjectDto = subjectService.findSubjectById(subjectId);
        BaseResponse<SubjectDto> subjectDtoBaseResponse = new BaseResponse<>();
        return subjectDtoBaseResponse
                .buildResponseEntity(HttpStatus.OK, "Subject found",  subjectDto);
    }

    /**
     * Get Subject by its name.
     * @param subjectName String subjectName
     * @return Response SubjectDto
     */
    @GetMapping(params = "subjectName")
    public ResponseEntity<? extends Response<SubjectDto>> findSubjectByName(
            @RequestParam("subjectName") final String subjectName){

        SubjectDto subjectDto = subjectService.findSubjectByName(subjectName);
        BaseResponse<SubjectDto> subjectDtoBaseResponse = new BaseResponse<>();
        return subjectDtoBaseResponse
                .buildResponseEntity(HttpStatus.OK, "Subject found.", subjectDto);
    }

    /**
     * DELETE handler method for deleting a Subject by its ID.
     * @param subjectId String
     * @return Void
     */
    @DeleteMapping(path = "/{subjectId}")
    public ResponseEntity<? extends Response<Void>> deleteSubjectById(@PathVariable String subjectId){

        subjectService.deleteSubjectById(subjectId);
        BaseResponse<Void> subjectResponse = new BaseResponse<>();
        return subjectResponse
                .buildResponseEntity(HttpStatus.OK, "Subject deleted successfully.", null);
    }
}
