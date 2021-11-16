package com.example.dto;

import com.example.model.Subject;
import com.example.model.status.ModelStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Student DTO class to encapsulate implementation of entity.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
@JsonSerialize
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    @JsonProperty("studentId")
    private String studentId;

    @JsonProperty(required = true)
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 32)
    private String studentName;

    @JsonProperty(required = true)
    @Positive
    @NotNull
    private Integer studentAge;

    @JsonProperty(required = true)
    @NotBlank
    @NotEmpty
    @Size(min = 4, max = 64)
    @Email(message = "Student email must be valid")
    private String studentEmail;

    @JsonProperty("studentStatus")
    private ModelStatus studentStatus;

    @JsonProperty("studentSubjects")
    private List<Subject> studentSubjects = new ArrayList<>();

}
