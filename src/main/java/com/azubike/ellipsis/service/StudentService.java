package com.azubike.ellipsis.service;

import java.util.List;
import java.util.Optional;

import com.azubike.ellipsis.entity.Student;
import com.azubike.ellipsis.request.InQueryRequest;
import com.azubike.ellipsis.request.StudentRequest;

public interface StudentService {
	List<Student> getAllStudents(int page, int size);

	Student createStudent(StudentRequest student);

	Student updateStudent(StudentRequest studentRequest, long id);

	void deleteStudent(long id);

	List<Student> findByFirstName(String name);

	Student findByFirstNameAndLastName(String firstName, String lastName);

	List<Student> findByFirstNameOrLastName(String firstName, String lastName);

	List<Student> findByFirstNameIn(InQueryRequest firstNames);

	Optional<Student> updateStudentFirstName(long id, String firstName);

	List<Student> saveAllStudents(List<StudentRequest> students);
	
	List<Student> findByCity(String city);

}
