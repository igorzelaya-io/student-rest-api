package com.example.dto;

import com.example.model.Subject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

/**
 * Teacher Dto class used to represent Teacher entity.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
@Builder
@Getter
@JsonSerialize
public class TeacherDto {

    @JsonProperty("teacherId")
    private String teacherId;

    @JsonProperty(required = true)
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 32)
    private String teacherName;

    @JsonProperty(required = true)
    @NotNull
    @Positive
    private Integer teacherAge;

    @Email(message = "Teacher email should be valid")
    @NotBlank
    @NotEmpty
    @Size(min = 5, max = 64)
    @JsonProperty(required = true)
    private String teacherEmail;

    @JsonProperty("teacherSubjects")
    private List<Subject> teacherSubjects;

}
