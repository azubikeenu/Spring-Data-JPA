package com.azubike.ellipsis.service;

import java.util.List;

import com.azubike.ellipsis.entity.Student;
import com.azubike.ellipsis.request.StudentRequest;

public interface StudentService {
	List<Student> getAllStudents();

	Student createStudent(StudentRequest student);

	Student updateStudent(StudentRequest studentRequest, long id);

}
