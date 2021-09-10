package com.example.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

	private TeacherRepository teacherRepository;
	
	@Autowired
	public TeacherService(TeacherRepository teacherRepository) {
		this.teacherRepository = teacherRepository;
	}
	
	void saveTeacher(Teacher teacher) {
		this.teacherRepository.save(teacher);
	}
	
}
