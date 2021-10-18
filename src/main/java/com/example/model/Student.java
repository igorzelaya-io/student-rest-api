package com.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import com.example.model.status.ModelStatus;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

/**
 * Student class to represent Student entity.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
@Entity
@Table(name = "students")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Student {

	@Id
	@Column(name = "student_id", nullable = false, unique = true)
	private String studentId;

	@Column(name = "student_name", nullable = false)
	private String studentName;

	@Column(name = "student_email")
	private String studentEmail;

	@Column(name = "student_age", nullable = false)
	private Integer studentAge;

	@ManyToMany(fetch = FetchType.LAZY,
				cascade = CascadeType.ALL)
	@JoinTable(
		name = "students_subjects",
		joinColumns = @JoinColumn(name = "student_id"),
		inverseJoinColumns = @JoinColumn(name = "subject_id")
	)
	private List<Subject> studentSubjects = new ArrayList<>();

	@Column(name = "student_status", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private ModelStatus studentStatus;

	/**
	 * Adds fields which are not populated by Student DTO.
	 * @return
	 */
	public static Student buildFromDto(Student studentDto){
		studentDto.setStudentId(UUID.randomUUID().toString());
		studentDto.setStudentStatus(ModelStatus.ACTIVE);
		return studentDto;
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

	private void setStudentId(final String studentId){
		this.studentId = studentId;
	}

	public void setStudentStatus(ModelStatus modelStatus){
		this.studentStatus = modelStatus;
	}

}
