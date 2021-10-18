package com.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.dto.TeacherDto;
import com.example.model.Subject;
import com.example.model.status.ModelStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@Getter
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

	public static Teacher buildFromDto(Teacher teacherDto){
		teacherDto.setTeacherId(UUID.randomUUID().toString());
		teacherDto.setTeacherStatus(ModelStatus.ACTIVE);
		return teacherDto;
	}

	private void setTeacherId(final String teacherId){
		this.teacherId = teacherId;
	}

	public void setTeacherStatus(ModelStatus modelStatus){
		this.teacherStatus = modelStatus;
	}
	
}
