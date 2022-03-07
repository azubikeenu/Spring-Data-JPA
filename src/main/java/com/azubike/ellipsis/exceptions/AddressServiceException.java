package com.azubike.ellipsis.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressServiceException extends RuntimeException {

	private static final long serialVersionUID = 7140517049919307989L;
	private int statusCode;

	public AddressServiceException() {
		// TODO Auto-generated constructor stub
	}

	public AddressServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AddressServiceException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	public AddressServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public AddressServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
