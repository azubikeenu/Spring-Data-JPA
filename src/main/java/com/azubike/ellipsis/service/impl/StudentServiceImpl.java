package com.azubike.ellipsis.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.azubike.ellipsis.entity.Student;
import com.azubike.ellipsis.exceptions.StudentServiceException;
import com.azubike.ellipsis.repository.StudentRepository;
import com.azubike.ellipsis.request.StudentRequest;
import com.azubike.ellipsis.response.ErrorMessages;
import com.azubike.ellipsis.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentRepository repository;

	@Override
	public List<Student> getAllStudents() {
		return repository.findAll();
	}

	@Override
	public Student createStudent(StudentRequest studentRequest) {
		if (repository.findByEmail(studentRequest.getEmail()) != null)
			throw new StudentServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessages(),
					HttpStatus.BAD_REQUEST.value());
		Student student = new ModelMapper().map(studentRequest, Student.class);
		Student returnedValue = repository.save(student);
		return returnedValue;
	}

	@Override
	public Student updateStudent(StudentRequest studentRequest, long id) {
		Student foundStudent = repository.findById(id)
				.orElseThrow(() -> new StudentServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages(),
						HttpStatus.BAD_REQUEST.value()));
		if (StringUtils.isNoneBlank(studentRequest.getFirstName(), studentRequest.getLastName())) {
			foundStudent.setFirstName(studentRequest.getFirstName());
			foundStudent.setLastName(studentRequest.getLastName());
		}
		Student updatedStudent = repository.save(foundStudent);
		return updatedStudent;

	}

}
