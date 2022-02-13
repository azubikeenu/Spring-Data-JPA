package com.azubike.ellipsis.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.azubike.ellipsis.response.ErrorMessage;

@ControllerAdvice
public class AppExceptionHandler {
	@ExceptionHandler(value = { StudentServiceException.class })
	public ResponseEntity<Object> handleStudentServiceException(StudentServiceException ex, WebRequest req) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage(), ex.getStatusCode());
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest req) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}