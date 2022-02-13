package com.azubike.ellipsis.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private int statusCode;

	public StudentServiceException() {
		super();
	}

	public StudentServiceException(String message) {
		super(message);
	}

	public StudentServiceException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

}