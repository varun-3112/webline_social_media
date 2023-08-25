package com.webline.socialmedia.entity;

import org.springframework.stereotype.Component;

@Component
public class JwtResponse {
	private String jwtResponse;
	private String userName;
	private String errorMessage;

	public JwtResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtResponse(String jwtResponse, String userName, String errorMessage) {
		super();
		this.jwtResponse = jwtResponse;
		this.userName = userName;
		this.errorMessage = errorMessage;
	}

	public String getJwtResponse() {
		return jwtResponse;
	}

	public void setJwtResponse(String jwtResponse) {
		this.jwtResponse = jwtResponse;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
