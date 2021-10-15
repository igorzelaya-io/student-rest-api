package com.example.service.implementation;import com.example.dto.StudentDto;import com.example.exception.StudentNotFoundException;import com.example.model.Student;import com.example.model.mapper.StudentMapper;import com.example.model.status.ModelStatus;import com.example.repository.StudentRepository;import com.example.service.StudentService;import lombok.RequiredArgsConstructor;import org.springframework.stereotype.Service;/** * Service class for Student entity. * @author Igor A. Zelaya (izelaya22@gmail.com) * @version 1.0.0 */@Service@RequiredArgsConstructorpublic class StudentServiceImpl implements StudentService {	private StudentRepository studentRepository;	private StudentMapper studentMapper;	@Override	public void saveStudent(StudentDto studentDto) {		Student student = Student.buildFromDto(this.studentMapper.dtoToStudent(studentDto));		this.studentRepository.save(student);	}	@Override	public StudentDto findStudentById(String studentId) {		if(studentExists(studentId)){			return studentMapper.studentToDto(studentRepository.findById(studentId)					.orElseThrow(() -> StudentNotFoundException.buildStudentNotFoundExceptionForId(studentId)));		}		throw StudentNotFoundException.buildStudentNotFoundExceptionForId(studentId);	}	@Override	public StudentDto findStudentByName(String studentName) {		Student student = this.studentRepository.findByStudentName(studentName)				.orElseThrow(() -> StudentNotFoundException.buildStudentNotFoundExceptionForField("studentName", studentName));		if(student.getStudentStatus().equals(ModelStatus.ACTIVE)){			return studentMapper.studentToDto(student);		}		throw StudentNotFoundException.buildStudentNotFoundExceptionForField("studentName", studentName);	}	@Override	public void deleteStudentById(String studentId){		Student student = studentMapper.dtoToStudent(findStudentById(studentId));		student.setStudentStatus(ModelStatus.INACTIVE);		studentRepository.save(student);	}	/**	 * Validates if student exists by its ID.	 * @param studentId String	 * @return true or false	 */	private boolean studentExists(String studentId) {		if(studentRepository.existsById(studentId)){			Student student = studentRepository.findById(studentId).get();			return student.getStudentStatus().equals(ModelStatus.ACTIVE);		}		return false;	}	/**	 * Evaluate if Student status is ACTIVE.	 * @param student Student	 * @return boolean	 */	private boolean isActiveStudent(Student student){		return student.getStudentStatus().getStatusCode() == 0;	}}