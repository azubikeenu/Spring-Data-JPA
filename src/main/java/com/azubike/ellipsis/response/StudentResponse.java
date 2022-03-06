package com.azubike.ellipsis.response;

import com.azubike.ellipsis.entity.Address;
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
	private Address address;

}
