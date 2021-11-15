package com.example.dto.openapi;

import com.example.dto.StudentDto;
import com.example.dto.pageable.PageResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PageResponseStudentDto")
public class PageResponseStudentDto extends PageResponseDto<StudentDto> { }
