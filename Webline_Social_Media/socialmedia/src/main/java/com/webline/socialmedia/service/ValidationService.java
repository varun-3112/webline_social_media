package com.webline.socialmedia.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.webline.socialmedia.entity.JwtRequest;
import com.webline.socialmedia.entity.Post;
import com.webline.socialmedia.entity.RegisterRequest;

@Service
public class ValidationService {

	public String validateLoginRequest(JwtRequest request) {
		String errorMessage = "";

		if (request.getUserName() == null || request.getUserName().isEmpty()) {
			errorMessage += "User Name is mandatory.";
		}
		if (request.getPassword() == null || request.getPassword().isEmpty()) {
			errorMessage += "Password is mandatory.";
		}
		return errorMessage;
	}

	public String validateRegisterRequest(RegisterRequest request) {
		String errorMessage = "";

		if (request.getUserName() == null || request.getUserName().isEmpty()) {
			errorMessage += "User Name is mandatory.";
		}
		if (request.getPassword() == null || request.getPassword().isEmpty()) {
			errorMessage += "Password is mandatory.";
		}
		if (request.getEmail() == null || request.getEmail().isEmpty()) {
			errorMessage += "User Name is mandatory.";
		} else {
			if (!validateEmail(request.getEmail())) {
				errorMessage += "Email is not valid";
			}
		}
		if (request.getRole() == null || request.getRole().isEmpty()) {
			errorMessage += "Role is mandatory.";
		} else {
			if (!request.getRole().equals("ROLE_ADMIN") && !request.getRole().equals("ROLE_USER")) {
				errorMessage += "Role can only be 'ROLE_ADMIN' or 'ROLE_USER'";
			}
		}

		return errorMessage;
	}

	boolean validateEmail(String email) {
		//checking if user email is proper
		Pattern pattern = Pattern.compile("(^[A-Za-z0-9._+-]+@[A-Za-z0-9._-]+\\.[A-Za-z]+$)");
		Matcher matcher = pattern.matcher(email.trim());
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

}
