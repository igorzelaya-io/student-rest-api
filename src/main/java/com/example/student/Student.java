package com.example.student;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.subject.Subject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;

@Entity
@JsonSerialize
@Table(name = "students")
@Builder
public class Student {

	@Id
	@JsonProperty("studentId")
	@Column(name = "student_id", nullable = false, unique = true)
	private String studentId;
	
	@JsonProperty
	@Column(name = "student_name", nullable = false)
	@NotBlank
	@Size(min = 2, max = 32)
	private String studentName;
	
	@JsonProperty
	@Column(name = "student_age", nullable = false)
	@NotNull
	private Integer studentAge;
	
	
	@ManyToMany(fetch = FetchType.LAZY,
				cascade = CascadeType.ALL)
	@JoinTable(
		name = "students_subjects",
		joinColumns = @JoinColumn(name = "student_id"),
		inverseJoinColumns = @JoinColumn(name = "subject_id")
	)
	private List<Subject> studentSubjects;
	
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
				&& (this.studentName.equals(student.studentName))
				&& (this.studentAge.equals(student.studentAge));
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

	public Integer getStudentAge() {
		return studentAge;
	}

	public void setStudentAge(Integer studentAge) {
		this.studentAge = studentAge;
	}
}
