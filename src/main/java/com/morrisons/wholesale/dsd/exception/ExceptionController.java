package com.morrisons.wholesale.dsd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

	
	
	@ExceptionHandler(value = ConfigServiceException.class)
	public ResponseEntity<Object> exception(ConfigServiceException exception) {

		exception.getErrorCode();
		return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
	}
}