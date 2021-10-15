package com.example.dto;

import com.example.model.Student;
import com.example.model.Teacher;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Subject class used as DTO for Subject entity.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
@Builder
@Getter
@JsonSerialize
public class SubjectDto {

    @JsonProperty("subjectId")
    private String subjectId;

    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 32)
    @JsonProperty(required = true)
    private String subjectName;

    @JsonProperty("subjectStudents")
    private List<Student> subjectStudents;

    @JsonProperty("teacher")
    private Teacher teacher;

}
