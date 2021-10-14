package com.example.service;

import java.util.List;

import com.example.exception.TeacherNotFoundException;
import com.example.model.Teacher;
import com.example.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {

	private final TeacherRepository teacherRepository;
	
	public void saveTeacher(Teacher teacher) {
		this.teacherRepository.save(teacher);
	}
	
	private boolean teacherExists(String teacherId) {
		return this.teacherRepository.existsById(teacherId);
	}
	
	public Teacher findTeacherById(final String teacherId) {
		return this.teacherRepository.findById(teacherId)
									 .orElseThrow(() ->
											 TeacherNotFoundException.buildTeacherNotFoundExceptionForId(teacherId));
	}
	
	public Teacher findTeacherByName(final String teacherName) {
		return this.teacherRepository.findByTeacherName(teacherName)
									 .orElseThrow(() -> TeacherNotFoundException
													.buildTeacherNotFoundExceptionForField
															("teacherName", teacherName));
	}

	public void deleteTeacherById(final String teacherId){
		this.teacherRepository.deleteById(teacherId);
	}
	
	public List<Teacher> findAllTeachers(){
		return this.teacherRepository.findAll();
	}
	
}
