package com.example.dto.openapi;

import com.example.dto.StudentDto;
import com.example.response.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class used to define @Schema for StudentDto response wrapper in openapi documentation.
 */
@Schema(name = "ResponseStudentDto")
public class ResponseStudentDto extends BaseResponse<StudentDto> { }
