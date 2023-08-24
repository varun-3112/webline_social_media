package com.webline.socialmedia.service;

import org.springframework.stereotype.Service;

import com.webline.socialmedia.entity.JwtRequest;
import com.webline.socialmedia.entity.Post;
import com.webline.socialmedia.entity.RegisterRequest;

@Service
public class ValidationService {

	public String validatePostRequest(Post post) {
		String errorMessage="";
		
		if(post.getPostTitle()==null || post.getPostTitle().isEmpty()) {
			errorMessage+="Post title is mandatory.";
		}
		
		if(post.getPostContent()==null || post.getPostContent().isEmpty()) {
			errorMessage+="Post content is mandatory.";
		}
		return errorMessage;
	}
	
	public String validateLoginRequest(JwtRequest request) {
	String errorMessage="";
		
		if(request.getUserName()==null || request.getUserName().isEmpty()) {
			errorMessage+="User Name is mandatory.";
		}
		if(request.getPassword()==null || request.getPassword().isEmpty()) {
			errorMessage+="Password is mandatory.";
		}
		return errorMessage;
	}
	

	public String validateRegisterRequest(RegisterRequest request) {
		String errorMessage="";
		
		if(request.getUserName()==null || request.getUserName().isEmpty()) {
			errorMessage+="User Name is mandatory.";
		}
		if(request.getPassword()==null || request.getPassword().isEmpty()) {
			errorMessage+="Password is mandatory.";
		}
		if(request.getEmail()==null || request.getEmail().isEmpty()) {
			errorMessage+="User Name is mandatory.";
		}else {
			if(!validateEmail(request.getEmail())) {
				errorMessage+="Email is not valid";
			}
		}
		if(request.getUserName()==null || request.getUserName().isEmpty()) {
			errorMessage+="User Name is mandatory.";
		}
		
		return errorMessage;
	}
	
	boolean validateEmail(String email) {
		return true;
	}

}
