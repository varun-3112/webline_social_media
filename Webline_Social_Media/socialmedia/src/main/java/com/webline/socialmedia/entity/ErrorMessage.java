package com.webline.socialmedia.entity;

import org.springframework.http.HttpStatus;

public class ErrorMessage {
	private HttpStatus status;
	private String message;

	public HttpStatus getStatus() {
		return status;
	}

	public ErrorMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErrorMessage(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
