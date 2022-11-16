package com.example.service.implementation;

import com.example.dto.SubjectDto;
import com.example.dto.TeacherDto;
import com.example.model.Subject;
import com.example.model.Teacher;
import com.example.model.mapper.SubjectMapper;
import com.example.model.mapper.TeacherMapper;
import com.example.model.status.ModelStatus;
import com.example.repository.TeacherRepository;
import com.example.service.SubjectService;
import com.example.service.TeacherService;
import com.example.utils.MessageKey;
import com.example.utils.Messages;
import com.example.utils.SortingPagingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

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

	private final SubjectService subjectService;

	private final SubjectMapper subjectMapper;

	private final SortingPagingUtils sortingPagingUtils;

	private final Messages messages;

	@Override
	public Page<TeacherDto> findPaginatedSortedTeachers(final String teacherName, final int page,
														final int size, final String[] sort) {

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
	public TeacherDto saveTeacher(final TeacherDto teacherDto) {
		Teacher teacher = Teacher
				.buildFromDto(teacherMapper.dtoToTeacher(teacherDto));
		teacherRepository.save(teacher);
		return teacherMapper.toTeacherDto(teacher);
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
	public void addSubjectToTeacher(final String teacherId, final SubjectDto subjectDto) {
		Teacher teacher = teacherMapper
				.dtoToTeacher(findTeacherById(teacherId));
		Subject subject;
		if(subjectService.subjectExists(subjectDto.getSubjectName())) {
			subject = subjectMapper
					.dtoToSubject(subjectService
							.findSubjectByName(subjectDto.getSubjectName(), ModelStatus.ACTIVE.getStatusCode()));
		} else {
			subject = Subject.buildFromDto(subjectMapper.dtoToSubject(subjectDto));
		}
		teacher.addSubject(subject);
		teacherRepository.save(teacher);
	}

	@Override
	public void removeSubjectFromTeacher
			(final String teacherId, final String subjectId){

		Teacher teacher = teacherMapper
				.dtoToTeacher(findTeacherById(teacherId));
		Subject subject = subjectMapper
				.dtoToSubject(subjectService.findSubjectById(subjectId, ModelStatus.ACTIVE.getStatusCode()));

		if(Objects.nonNull(subject.getTeacher()) && subject.getTeacher().getTeacherId().equals(teacher.getTeacherId())){
			teacher.removeSubject(subject);
			teacherRepository.save(teacher);
			subjectService.updateSubject(subject);
			return;
		}
		throw new IllegalArgumentException(String.format(messages
				.getMessage(MessageKey.TEACHER_INVALID_SUBJECT.getKey()), subjectId));
	}

	@Override
	public void deleteTeacherById(final String teacherId) {
		Teacher teacher = teacherMapper
				.dtoToTeacher(findTeacherById(teacherId));
		teacher.setTeacherStatus(ModelStatus.INACTIVE);
		teacherRepository.save(teacher);
	}

	/**
	 * Evaluate if Teacher status is ACTIVE.
	 * @param queryField String
	 * @param teacher String
	 * @param queryFieldValue String
	 * @return Teacher
	 */
	private Teacher isActiveTeacher(final Teacher teacher, final String queryField, final String queryFieldValue){
		if(teacher.getTeacherStatus().getStatusCode() == 0){
			return teacher;
		}
		throw TeacherNotFoundException
				.buildTeacherNotFoundExceptionForField(queryField, queryFieldValue);
	}
}
