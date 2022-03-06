package com.azubike.ellipsis.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsRequest {
	private List<StudentRequest> students;
}
