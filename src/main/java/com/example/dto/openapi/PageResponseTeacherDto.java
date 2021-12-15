package com.example.dto.openapi;

import com.example.dto.TeacherDto;
import com.example.dto.pageable.PageResponseDto;
import com.example.response.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PageResponseTeacherDto")
public class PageResponseTeacherDto extends PageResponseDto<TeacherDto> { }
