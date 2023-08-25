package com.webline.socialmedia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.webline.socialmedia.entity.ErrorMessage;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(UserExistException.class)
	public ResponseEntity<ErrorMessage> userExistException(UserExistException exception, WebRequest req){
		ErrorMessage error=new ErrorMessage(HttpStatus.FOUND, exception.getMessage());
		return ResponseEntity.status(HttpStatus.FOUND).body(error);
	}
	
	
	
	
}
