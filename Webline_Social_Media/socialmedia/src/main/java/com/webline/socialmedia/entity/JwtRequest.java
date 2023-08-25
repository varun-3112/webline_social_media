package com.webline.socialmedia.entity;

import org.springframework.stereotype.Component;

@Component
public class JwtRequest {
private String userName;
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