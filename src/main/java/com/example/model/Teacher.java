package com.example.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.model.Subject;
import com.example.model.status.ModelStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@JsonSerialize
@Builder
@AllArgsConstructor
@Getter
@Setter
@Table(name = "teachers")
public class Teacher {

	@Id
	@Column(name = "teacher_id", nullable = false, length = 32)
	private String teacherId;

	@Column(name = "teacher_name", nullable = false, length = 32)
	private String teacherName;

	@Column(name = "teacher_email")
	private String teacherEmail;

	@Column(name = "teacherAge")
	private Integer teacherAge;

	@OneToMany(mappedBy = "teacher")
	private List<Subject> teacherSubjects;

	@Column(name = "teacher_status")
	@Enumerated(EnumType.ORDINAL)
	private ModelStatus teacherStatus;
	
}
