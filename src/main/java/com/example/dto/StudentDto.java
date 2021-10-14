package com.example.dto;

import com.example.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {


    private String studentId;

    private String studentName;

    private Integer studentAge;

    private List<Subject> studentSubjects;

}
