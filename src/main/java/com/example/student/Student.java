package com.example.student;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@JsonSerialize
public class Student {

	@Id
	@JsonProperty("studentId")
	@Column(name = "student_id", nullable = false, unique = true)
	private String studentId;
	
	@JsonProperty
	@Column(name = "student_name", nullable = false)
	@NotNull
	@Size(min = 2, max = 32)
	private String studentName;
	
	@JsonProperty
	@Column(name = "student_age", nullable = false)
	@NotNull
	private Integer studentAge;
	
	public Student() {
		super();
		this.studentId = UUID.randomUUID().toString();
	}
	
	public Student(String studentId, String studentName, Integer studentAge) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentAge = studentAge;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null) return false;
		if(o.getClass() != this.getClass()) return false;
		Student student = (Student) o;
		return this.studentId == student.studentId
				&& (this.studentName == student.studentName)
				&& (this.studentAge == student.studentAge);
	}
	
	@Override 
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (this.studentName == null ? 0 : this.studentName.hashCode());
		hash = 31 * hash + (this.studentId == null ? 0 : this.studentId.hashCode());
		hash = 31 * hash + (this.studentAge == null ? 0 : this.studentAge.hashCode());
		return hash;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Student with ID: ")
					.append(this.studentId)
					.append(", and name: ")
					.append(this.studentName)
					.toString();
	}

	public String getStudentId() {
		return studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public Student studentName(String studentName) {
		this.setStudentName(studentName);
		return this;
	}

	public Integer getStudentAge() {
		return studentAge;
	}

	public void setStudentAge(Integer studentAge) {
		this.studentAge = studentAge;
	}
	
	public Student studentAge(Integer studentAge) {
		this.setStudentAge(studentAge);
		return this;
	}
}
