package com.example.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.model.Subject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@JsonSerialize
@Builder
@AllArgsConstructor
@Getter
@Table(name = "teachers")
public class Teacher {

	@Id
	@JsonProperty("teacherId")
	@Column(name = "teacher_id", nullable = false, length = 32)
	private String teacherId;
	
	@JsonProperty("teacherId")
	@Column(name = "teacher_name", nullable = false, length = 32)
	@NotBlank
	@Size(min = 2, max = 32)
	private String teacherName;
	
	@JsonProperty("teacherAge")
	@Size(min = 2, max = 32)
	@Column(name = "teacherAge")
	@NotNull
	private Integer teacherAge;
	
	@OneToMany(mappedBy = "teacher")
	private List<Subject> teacherSubjects;

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public void setTeacherAge(Integer teacherAge) {
		this.teacherAge = teacherAge;		
	}
	
}
