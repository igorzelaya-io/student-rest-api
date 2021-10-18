package com.example.service.implementation;

import com.example.dto.TeacherDto;
import com.example.exception.TeacherNotFoundException;
import com.example.model.Teacher;
import com.example.model.mapper.TeacherMapper;
import com.example.repository.TeacherRepository;
import com.example.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

	private final TeacherRepository teacherRepository;

	private final TeacherMapper teacherMapper;

	@Override
	public void saveTeacher(final TeacherDto teacherDto) {
		Teacher teacher = Teacher
				.buildFromDto(teacherMapper.dtoToTeacher(teacherDto));
		teacherRepository.save(teacher);
	}

	@Override
	public TeacherDto findTeacherById(final String teacherId) {
		Teacher teacher = teacherRepository.findById(teacherId)
				.orElseThrow(() -> TeacherNotFoundException
						.buildTeacherNotFoundExceptionForId(teacherId));
		return teacherMapper
				.toTeacherDto(
						isActiveTeacher(teacher, "teacherId", teacherId)
				);
	}

	@Override
	public TeacherDto findTeacherByName(final String teacherName) {
		Teacher teacher = teacherRepository.findByTeacherName(teacherName)
				.orElseThrow(() -> TeacherNotFoundException
						.buildTeacherNotFoundExceptionForField("teacherName", teacherName));
		return teacherMapper
				.toTeacherDto(
					isActiveTeacher(teacher, "teacherName", teacherName)
				);
	}

	@Override
	public void deleteTeacherById(String teacherId) {

	}

	private Teacher isActiveTeacher(Teacher teacher, String queryField, String queryFieldValue){
		if(teacher.getTeacherStatus().getStatusCode() == 0){
			return teacher;
		}
		throw TeacherNotFoundException
				.buildTeacherNotFoundExceptionForField(queryField, queryFieldValue);
	}
}
