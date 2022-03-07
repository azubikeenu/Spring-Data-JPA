package com.azubike.ellipsis.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.azubike.ellipsis.entity.Address;
import com.azubike.ellipsis.entity.Student;
import com.azubike.ellipsis.entity.Subject;
import com.azubike.ellipsis.exceptions.StudentServiceException;
import com.azubike.ellipsis.model.request.InQueryRequest;
import com.azubike.ellipsis.model.request.StudentRequest;
import com.azubike.ellipsis.model.response.ErrorMessages;
import com.azubike.ellipsis.repository.StudentRepository;
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
		Address address = new Address();
		address.setCity(studentRequest.getCity());
		address.setStreet(studentRequest.getStreet());
		Student student = new ModelMapper().map(studentRequest, Student.class);
		student.setAddress(address);

		List<Subject> subjects = studentRequest.getSubjects().stream().map(s -> {
			Subject subject = new Subject();
			subject.setMarksObtained(s.getMarksObtained());
			subject.setSubjectName(s.getSubjectName());
			subject.setStudent(student);
			return subject;
		}).collect(Collectors.toList());

		student.setSubjects(subjects);
		Student savedStudent = repository.save(student);

		return savedStudent;
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

	@Override
	public Optional<Student> updateStudentFirstName(long id, String firstName) {
		repository.updateStudentFirstName(firstName, id);
		return repository.findById(id);
	}

	@Override
	public List<Student> saveAllStudents(List<StudentRequest> studentRequests) {
		List<Address> addresses = new ArrayList<>();

		for (int i = 0; i < studentRequests.size(); i++) {
			Address address = new Address();
			address.setCity(studentRequests.get(i).getCity());
			address.setStreet(studentRequests.get(i).getStreet());
			addresses.add(address);
		}
		List<Student> students = mapStudents(new ArrayList<Student>(), studentRequests);

		for (int i = 0; i < addresses.size(); i++) {
			students.get(i).setAddress(addresses.get(i));
		}
		List<Student> savedStudents = repository.saveAll(students);
		return savedStudents;
	}

	private List<Student> mapStudents(List<Student> returnedValue, List<StudentRequest> students) {
		if (students != null && !students.isEmpty()) {
			Type listType = new TypeToken<List<Student>>() {
			}.getType();
			returnedValue = new ModelMapper().map(students, listType);
		}
		return returnedValue;
	}

	@Override
	public List<Student> findByCity(String city) {
		return repository.findByAddressCity(city);
	}

}
