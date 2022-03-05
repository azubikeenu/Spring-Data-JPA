package com.azubike.ellipsis.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.azubike.ellipsis.entity.Student;
import com.azubike.ellipsis.exceptions.StudentServiceException;
import com.azubike.ellipsis.repository.StudentRepository;
import com.azubike.ellipsis.request.InQueryRequest;
import com.azubike.ellipsis.request.StudentRequest;
import com.azubike.ellipsis.response.ErrorMessages;
import com.azubike.ellipsis.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentRepository repository;

	@Override
	public List<Student> getAllStudents(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
		Page<Student> pages = repository.findAll(pageable);
		return pages.getContent();
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

	@Override
	public void deleteStudent(long id) {
		Student foundStudent = repository.findById(id)
				.orElseThrow(() -> new StudentServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages(),
						HttpStatus.BAD_REQUEST.value()));
		repository.delete(foundStudent);
	}

	@Override
	public List<Student> findByFirstName(String name) {
		List<Student> students = repository.findStudentByFirstName(name);
		return students;
	}

	@Override
	public Student findByFirstNameAndLastName(String firstName, String lastName) {
		Student foundStudent = repository.findByFirstNameAndLastName(firstName, lastName);
		if (foundStudent == null)
			throw new StudentServiceException(ErrorMessages.NAME_NOT_FOUND.getErrorMessages(),
					HttpStatus.NOT_FOUND.value());
		return foundStudent;
	}

	@Override
	public List<Student> findByFirstNameOrLastName(String firstName, String lastName) {
		return repository.findByFirstNameOrLastName(firstName, lastName);
	}

	@Override
	public List<Student> findByFirstNameIn(InQueryRequest firstNames) {
		List<Student> students = repository.findByFirstNameIn(firstNames.getFirstNames());
		return students;
	}

}
