package com.azubike.ellipsis.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentResponse {
	private long id;
	@JsonProperty(value = "first_name")
	private String firstName;
	private String lastName;
	private String email;
	private AddressResponse address;
	private List<SubjectResponse> subjects;

}
