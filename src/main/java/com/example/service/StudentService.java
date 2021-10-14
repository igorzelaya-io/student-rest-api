package com.example.service;import java.util.List;import com.example.exception.StudentNotFoundException;import com.example.model.Student;import com.example.repository.StudentRepository;import lombok.RequiredArgsConstructor;import org.springframework.stereotype.Service;/** * Service class for Student entity. * @author Igor A. Zelaya (izelaya22@gmail.com) * @version 1.0.0 */@Service@RequiredArgsConstructorpublic class StudentService {	private StudentRepository studentRepository;	/**	 * Validates if student exists by its ID.	 * @param studentId String	 * @return true or false	 */	public boolean studentExists(String studentId) {		return studentRepository.existsById(studentId);	}	/**	 * Saves given Student into DB.	 * @param student Student	 */	public void saveStudent(Student student) {		this.studentRepository.save(student);	}	/**	 * Find a student by its ID.	 * @param studentId String	 * @return Student	 * @throws StudentNotFoundException when no Student is found by ID	 */	public Student findStudentById(String studentId) {		return studentRepository.findById(studentId)								.orElseThrow(() -> StudentNotFoundException												.buildStudentNotFoundExceptionForField("id", studentId));	}	/**	 * Find a student by its name.	 * @param studentName String	 * @return Student	 * @throws StudentNotFoundException when no Student is found by name	 */	public Student findStudentByName(String studentName) {		return studentRepository.findByStudentName(studentName)				.orElseThrow(() ->  StudentNotFoundException						.buildStudentNotFoundExceptionForField("name", studentName));	}	/**	 * Deletes a student by its Id.	 * @param studentId String	 */	public void deleteStudent(String studentId){		studentRepository.deleteById(studentId);	}}