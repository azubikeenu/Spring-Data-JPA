package com.azubike.ellipsis.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.entity.Student;
import com.azubike.ellipsis.exceptions.StudentServiceException;
import com.azubike.ellipsis.model.request.InQueryRequest;
import com.azubike.ellipsis.model.request.StudentRequest;
import com.azubike.ellipsis.model.request.StudentsRequest;
import com.azubike.ellipsis.model.response.ErrorMessages;
import com.azubike.ellipsis.model.response.OperationStatusModel;
import com.azubike.ellipsis.model.response.RequestOperationName;
import com.azubike.ellipsis.model.response.RequestOperationStatus;
import com.azubike.ellipsis.model.response.StudentResponse;
import com.azubike.ellipsis.service.StudentService;

@RestController
@RequestMapping("/api/v1/students")
@Validated
public class StudentController {
	@Autowired
	private StudentService studentService;

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StudentResponse>> getStudents(@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		List<StudentResponse> returnedValue = new ArrayList<>();
		List<Student> students = studentService.getAllStudents(page, size);
		returnedValue = mapStudents(returnedValue, students);
		return ResponseEntity.ok().body(returnedValue);

	}

	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StudentResponse> createStudent(
			@RequestBody(required = true) @Valid StudentRequest studentRequest) {
		Student savedStudent = studentService.createStudent(studentRequest);
		StudentResponse returnedValue = new ModelMapper().map(savedStudent, StudentResponse.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(returnedValue);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StudentResponse> updateStudent(
			@RequestBody(required = true) @Valid StudentRequest studentRequest,
			@PathVariable(name = "id", required = true) long id) {
		Student updatedStudent = studentService.updateStudent(studentRequest, id);
		StudentResponse returnedValue = new ModelMapper().map(updatedStudent, StudentResponse.class);
		return ResponseEntity.ok().body(returnedValue);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OperationStatusModel> deleteStudent(@PathVariable(name = "id", required = true) long id) {
		OperationStatusModel returnedValue = new OperationStatusModel();
		studentService.deleteStudent(id);
		returnedValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		returnedValue.setOperationName(RequestOperationName.DELETE.name());
		return ResponseEntity.ok().body(returnedValue);
	}

	@GetMapping(value = "/find_by_first_name", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StudentResponse>> findStudentByName(
			@RequestParam(name = "name", required = true) String name) {
		List<StudentResponse> returnedValue = new ArrayList<>();
		List<Student> students = studentService.findByFirstName(name);
		returnedValue = mapStudents(returnedValue, students);
		return ResponseEntity.ok().body(returnedValue);
	}

	@GetMapping(value = "/find_by_first_name_and_last_name", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StudentResponse> findByFirstNameAndLastName(
			@RequestParam(name = "firstName", required = true) String firstName,
			@RequestParam(name = "lastName") String lastName) {
		Student student = studentService.findByFirstNameAndLastName(firstName, lastName);
		StudentResponse returnedValue = new ModelMapper().map(student, StudentResponse.class);
		return ResponseEntity.ok(returnedValue);
	}

	@GetMapping(value = "/find_by_first_name_or_last_name", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StudentResponse>> findByFirstNameOrLastName(
			@RequestParam(name = "firstName", required = true) String firstName,
			@RequestParam(name = "lastName") String lastName) {
		List<StudentResponse> returnedValue = new ArrayList<>();
		List<Student> students = studentService.findByFirstNameOrLastName(firstName, lastName);
		returnedValue = mapStudents(returnedValue, students);
		return ResponseEntity.ok().body(returnedValue);

	}

	@GetMapping(value = "/find_by_first_name_in", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StudentResponse>> findByFirstNameIn(@Valid @RequestBody InQueryRequest firstNames) {
		List<StudentResponse> returnedValue = new ArrayList<>();
		List<Student> students = studentService.findByFirstNameIn(firstNames);
		returnedValue = mapStudents(returnedValue, students);
		return ResponseEntity.ok().body(returnedValue);

	}

	@PutMapping(value = "/update_student_first_name/{id}/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StudentResponse> updateStudentFirstName(@PathVariable(name = "id", required = true) long id,
			@PathVariable(required = true) String firstName) {
		Student updatedStudent = studentService.updateStudentFirstName(id, firstName)
				.orElseThrow(() -> new StudentServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages(),
						HttpStatus.NOT_FOUND.value()));

		StudentResponse returnedValue = new ModelMapper().map(updatedStudent, StudentResponse.class);
		return ResponseEntity.ok(returnedValue);
	}

	@PostMapping(value = "/save_students", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StudentResponse>> saveMultipleStudents(
			@RequestBody(required = true) StudentsRequest students) {
		List<Student> savedStudent = studentService.saveAllStudents(students.getStudents());
		List<StudentResponse> returnedValue = mapStudents(new ArrayList<StudentResponse>(), savedStudent);
		return ResponseEntity.ok(returnedValue);

	}

	@GetMapping(value = "/find_by_city/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<StudentResponse>> findStudentsByCity(@PathVariable String city) {
		List<Student> students = studentService.findByCity(city);
		List<StudentResponse> returnedValue = mapStudents(new ArrayList<StudentResponse>(), students);
		return ResponseEntity.ok(returnedValue);

	}

	private List<StudentResponse> mapStudents(List<StudentResponse> returnedValue, List<Student> students) {
		if (students != null && !students.isEmpty()) {
			Type listType = new TypeToken<List<StudentResponse>>() {
			}.getType();
			returnedValue = new ModelMapper().map(students, listType);
		}

		return returnedValue;
	}

}
