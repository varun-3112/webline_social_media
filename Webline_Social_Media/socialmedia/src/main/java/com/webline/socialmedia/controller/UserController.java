package com.webline.socialmedia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webline.socialmedia.config.JwtService;
import com.webline.socialmedia.entity.JwtRequest;
import com.webline.socialmedia.entity.JwtResponse;
import com.webline.socialmedia.entity.RegisterRequest;
import com.webline.socialmedia.entity.User;
import com.webline.socialmedia.entity.UserDto;
import com.webline.socialmedia.exception.UserExistException;
import com.webline.socialmedia.service.UserService;
import com.webline.socialmedia.service.ValidationService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ValidationService validationService;

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/auth/register")
	public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request) throws UserExistException {

		String errorMessage = validationService.validateRegisterRequest(request);
		if (errorMessage!=null && !errorMessage.isEmpty()) {
			JwtResponse jwtResponse = new JwtResponse(null , request.getUserName(),errorMessage);
			return new ResponseEntity<>(jwtResponse, HttpStatus.BAD_REQUEST);
		}else {
			User user = userService.checkUser(request.getUserName());
			User savedUser;
			if (user == null) {
				//New User
				user=new User(request.getUserName(),request.getPassword(),user.getEmail(),user.getRole());
				savedUser = userService.addUser(user);
				
				if(savedUser!=null){
					String jwt=jwtService.generateToken(user);
					JwtResponse jwtResponse = new JwtResponse(jwt, user.getUserName(),null);
					return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
				}else {
					JwtResponse jwtResponse = new JwtResponse(null, request.getUserName(),"Error in registering user.");
					return new ResponseEntity<>(jwtResponse, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}else {
				JwtResponse jwtResponse = new JwtResponse(null, request.getUserName(),"User Already exist with same User Name");
				return new ResponseEntity<>(jwtResponse, HttpStatus.BAD_REQUEST);
			}
		}
	}

	@PostMapping("/auth/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) throws UserExistException {
		
		String errorMessage=validationService.validateLoginRequest(request);
		
		if(errorMessage!=null && !errorMessage.isEmpty()) {
			JwtResponse jwtResponse = new JwtResponse(null, request.getUserName(),errorMessage);
			return new ResponseEntity<>(jwtResponse, HttpStatus.BAD_REQUEST);
		}
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
		
		User user=userService.checkUser(request.getUserName());
		if(user==null) {
			JwtResponse jwtResponse = new JwtResponse(null, request.getUserName(),"User does not exist with this User Name");
			return new ResponseEntity<>(jwtResponse, HttpStatus.BAD_REQUEST);
		}else {
			String jwt=jwtService.generateToken(user);
			JwtResponse jwtResponse = new JwtResponse(jwt, user.getUserName(),null);
			return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
		}

	}

	@GetMapping("/allUsers")
	public ResponseEntity<List<UserDto>> getAllUsers(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) String pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) String pageSize,
			@RequestParam(value = "sortedBy", defaultValue = "USER_ID", required = false) String sortedBy,
			@RequestParam(value = "sortType", defaultValue = "ASC", required = false) String sortType) {
		List<UserDto> users = userService.getAllUsers(pageNumber, pageSize, sortedBy, sortType);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/allUsersById")
	public ResponseEntity<List<UserDto>> getAllUsersById(@RequestParam("id") List<Long> id) {
		List<UserDto> users = userService.getAllUsersById(id);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
}
