package com.azubike.ellipsis.service.impl;

import org.modelmapper.ModelMapper;

import com.azubike.ellipsis.entity.Student;
import com.azubike.ellipsis.request.StudentRequest;

public class TestClass {

	public static void main(String[] args) {
		StudentRequest student = new StudentRequest();

		student.setEmail("enuazubike88@gmail.com");
		student.setFirstName("Richard");
		student.setLastName("Enu");
		Student response = new ModelMapper().map(student, Student.class);
		System.out.println(response);

	}

}
