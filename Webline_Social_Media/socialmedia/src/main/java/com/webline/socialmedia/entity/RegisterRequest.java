package com.webline.socialmedia.entity;

import org.springframework.stereotype.Component;

import io.swagger.v3.oas.annotations.media.Schema;

@Component
public class RegisterRequest {
	@Schema(description = "User Name - Unique value allowed", example = "jack")
	private String userName;
	 
	 @Schema(description = "User password",example="jack")
	private String password;
	 
	 @Schema(description = "User Email", example = "jack@gmail.com")
	private String email;
	 
	 @Schema(description = "Role of the user - ROLE_ADMIN/ROLE_USER", example = "ROLE_ADMIN")
	private String role;
	
	 public RegisterRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RegisterRequest(String userName, String password, String email, String role) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.role = role;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
