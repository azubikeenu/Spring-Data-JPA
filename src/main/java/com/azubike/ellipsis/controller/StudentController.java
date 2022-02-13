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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.entity.Student;
import com.azubike.ellipsis.request.StudentRequest;
import com.azubike.ellipsis.response.StudentResponse;
import com.azubike.ellipsis.service.StudentService;

@RestController
@RequestMapping("/api/v1/students")
@Validated
public class StudentController {
	@Autowired
	private StudentService studentService;

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StudentResponse>> getStudents() {
		List<StudentResponse> returnedValue = new ArrayList<>();
		List<Student> students = studentService.getAllStudents();
		if (students != null && !students.isEmpty()) {
			Type listType = new TypeToken<List<StudentResponse>>() {
			}.getType();
			returnedValue = new ModelMapper().map(students, listType);
		}
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

}
