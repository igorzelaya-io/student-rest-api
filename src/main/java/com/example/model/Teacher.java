package com.example.model;

import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import com.example.model.status.ModelStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "teachers")
public class Teacher {

	@Id
	@Column(name = "teacher_id", nullable = false, length = 64)
	private String teacherId;

	@Column(name = "teacher_name", nullable = false, length = 32)
	private String teacherName;

	@Column(name = "teacher_email")
	private String teacherEmail;

	@Column(name = "teacherAge")
	private Integer teacherAge;

	@OneToMany(mappedBy = "teacher",
			cascade = { CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
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

	public void addSubject(Subject subject){
		this.teacherSubjects.add(subject);
		subject.setSubjectTeacher(this);
	}

	public void removeSubject(Subject subject){
		this.teacherSubjects.remove(subject);
		subject.setSubjectTeacher(null);
	}
}
