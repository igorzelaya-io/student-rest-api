package com.example.dto.openapi;

import com.example.dto.SubjectDto;
import com.example.dto.pageable.PageResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PageResponseSubjectDto")
public class PageResponseSubjectDto extends PageResponseDto<SubjectDto> {
}
