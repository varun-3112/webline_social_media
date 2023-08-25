package com.webline.socialmedia.entity;

import org.springframework.stereotype.Component;

import io.swagger.v3.oas.annotations.media.Schema;

@Component
public class JwtRequest {
	@Schema(description = "User Name", example = "jack")
	private String userName;

	@Schema(description = "Password", example = "jack")
	private String password;

	public JwtRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtRequest(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
