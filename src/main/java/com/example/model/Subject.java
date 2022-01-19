package com.example.model;

import java.util.ArrayList;
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
import com.example.model.status.ModelStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subjects")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Subject {

	@Id
	@Column(name = "subject_id", nullable = false, length = 64)
	private String subjectId;

	@Column(name = "subject_name", nullable = false, length = 32)
	private String subjectName;
	
	@ManyToMany(mappedBy = "studentSubjects",
				fetch = FetchType.EAGER,
				cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@Builder.Default
	private List<Student> subjectStudents = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;

	@Column(name = "subject_status")
	private ModelStatus subjectStatus;


	/**
	 * Build complete Subject object with given dto properties
	 * @param subjectDto Subject
	 * @return Subject
	 */
	public static Subject buildFromDto(Subject subjectDto){
		subjectDto.setSubjectId(UUID.randomUUID().toString());
		subjectDto.setSubjectStatus(ModelStatus.ACTIVE);
		return subjectDto;
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

	protected void setSubjectId(String subjectId){
		this.subjectId = subjectId;
	}

	public void setSubjectStatus(ModelStatus modelStatus){
		this.subjectStatus = modelStatus;
	}

	public void setSubjectTeacher(Teacher teacher){
		this.teacher = teacher;
	}
}
