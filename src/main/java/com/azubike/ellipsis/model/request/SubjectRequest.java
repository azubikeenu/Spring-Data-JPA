package com.azubike.ellipsis.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectRequest {
	@NotBlank
	private String subjectName;
	@NotBlank
	private int marksObtained;

}
