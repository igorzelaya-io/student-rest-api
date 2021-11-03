package com.example.service.implementation;

import com.example.dto.TeacherDto;
import com.example.exception.TeacherNotFoundException;
import com.example.model.Teacher;
import com.example.model.mapper.TeacherMapper;
import com.example.model.status.ModelStatus;
import com.example.repository.TeacherRepository;
import com.example.service.TeacherService;
import com.example.service.pagingSorting.TeacherPagingSortingService;
import com.example.utils.SortingPagingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation class for Teacher entity.
 * @author Igor A. Zelaya (izelaya22@gmail.com)
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

	private final TeacherRepository teacherRepository;

	private final TeacherMapper teacherMapper;

	private final SortingPagingUtils sortingPagingUtils;

	@Override
	public Page<TeacherDto> findPaginatedSortedTeachers(String teacherName, int page, int size, String[] sort) {
		List<Sort.Order> orders = sortingPagingUtils.getSortOrders(sort);
		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
		List<TeacherDto> teacherDtos;
		if(teacherName == null){
			teacherDtos = teacherMapper
					.teachersToDtos(teacherRepository.findAll(pageable).toList());
		}
		else {
			teacherDtos = teacherMapper
					.teachersToDtos(teacherRepository
							.findByTeacherNameContaining(teacherName, pageable).toList());
		}
		return new PageImpl<>(teacherDtos);
	}

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
		Teacher teacher = teacherMapper
				.dtoToTeacher(findTeacherById(teacherId));
		teacher.setTeacherStatus(ModelStatus.INACTIVE);
		teacherRepository.save(teacher);
	}

	/**
	 * Evaluate if Teacher status is ACTIVE.
	 * @param teacher String
	 * @param queryField String
	 * @param queryFieldValue String
	 * @return Teacher
	 */
	private Teacher isActiveTeacher(Teacher teacher, String queryField, String queryFieldValue){
		if(teacher.getTeacherStatus().getStatusCode() == 0){
			return teacher;
		}
		throw TeacherNotFoundException
				.buildTeacherNotFoundExceptionForField(queryField, queryFieldValue);
	}
}
