package com.azubike.ellipsis.model.request;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequest {
	@NotBlank(message = "first name is required")
	private String firstName;
	@NotBlank(message = "last name is required")
	private String lastName;
	@Email
	private String email;
	private String street;
	private String city;
	private List<SubjectRequest> subjects;

}
