package com.webline.socialmedia.entity;

import java.sql.Timestamp;

public class UserDto {
	
	private Long userId;
	
	private String userName;
	
	private String password;
	
	private String email;
	
	private String role;
	
	private Timestamp liked_disliked_time;

	public UserDto(Long userId, String userName, String password, String email, String role,
			Timestamp liked_disliked_time) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.role = role;
		this.liked_disliked_time = liked_disliked_time;
	}


	
	  public UserDto(Long userId,String userName, String email,String role,Timestamp liked_disliked_time) { 
		  super(); 
		  this.userId=userId;
		  this.userName =userName; 
		  this.email = email; 
		  this.role = role;
		  this.liked_disliked_time = liked_disliked_time;
	  }
	 


	public Long getUserId() {
		return userId;
	}
	

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Timestamp getLiked_disliked_time() {
		return liked_disliked_time;
	}

	public void setLiked_disliked_time(Timestamp liked_disliked_time) {
		this.liked_disliked_time = liked_disliked_time;
	}

}
