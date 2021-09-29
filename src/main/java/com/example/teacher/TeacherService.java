package com.example.teacher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;

@Service
public class TeacherService {

	private final TeacherRepository teacherRepository;
	
	@Autowired
	public TeacherService(TeacherRepository teacherRepository) {
		this.teacherRepository = teacherRepository;
	}
	
	void saveTeacher(Teacher teacher) {
		this.teacherRepository.save(teacher);
	}
	
	boolean teacherExists(String teacherId) {
		return this.teacherRepository.existsById(teacherId);
	}
	
	Teacher findTeacherById(String teacherId) {
		return this.teacherRepository.findById(teacherId)
									 .orElseThrow(() ->
											 TeacherNotFoundException.buildTeacherNotFoundExceptionForId(teacherId));
	}
	
	Teacher findTeacherByName(String teacherName) {
		return this.teacherRepository.findByTeacherName(teacherName)
									 .orElseThrow(() ->
											TeacherNotFoundException
													.buildTeacherNotFoundExceptionForField("teacherName", teacherName));
	}

	void deleteTeacherById(String teacherId){
		this.teacherRepository.deleteById(teacherId);
	}
	
	List<Teacher> findAllTeachers(){
		return this.teacherRepository.findAll();
	}
	
}
