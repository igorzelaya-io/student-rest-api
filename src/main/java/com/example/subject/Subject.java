package com.example.subject;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.student.Student;
import com.example.teacher.Teacher;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "subjects")
@JsonSerialize
public class Subject {

	@Id
	@Column(name = "subject_id", nullable = false, length = 32)
	@JsonProperty("subjectId")
	private String subjectId;
	
	
	@JsonProperty("subjectName")
	@Column(name = "subject_name", nullable = false, length = 32)
	@NotBlank
	@Size(min = 3, max = 12)
	private String subjectName;
	
	@ManyToMany(mappedBy = "studentSubjects",
				fetch = FetchType.EAGER,
				cascade = CascadeType.ALL)
	private List<Student> subjectStudents;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "teacher_id")
	@JsonProperty
	private Teacher teacher;
	
	public Subject() {
		super();
		this.subjectId = UUID.randomUUID().toString();
	}
	
	public Subject(String subjectName) {
		super();
		this.subjectName = subjectName;
		this.subjectId = UUID.randomUUID().toString();    
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subjectId == null) ? 0 : subjectId.hashCode());
		result = prime * result + ((subjectName == null) ? 0 : subjectName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subject other = (Subject) obj;
		if (subjectId == null) {
			if (other.subjectId != null)
				return false;
		} else if (!subjectId.equals(other.subjectId))
			return false;
		if (subjectName == null) {
			if (other.subjectName != null)
				return false;
		} else if (!subjectName.equals(other.subjectName))
			return false;
		return true;
	}
	
	public String getSubjectId() {
		return subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	public Subject subjectName(String subjectName) {
		this.setSubjectName(subjectName);
		return this;
	}

	public List<Student> getSubjectStudents() {
		return subjectStudents;
	}
	
	public Subject addSubjectStudent(Student student) {
		this.subjectStudents.add(student);
		return this;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	public Subject teacher(Teacher teacher) {
		this.setTeacher(teacher);
		return this;
	}
}
