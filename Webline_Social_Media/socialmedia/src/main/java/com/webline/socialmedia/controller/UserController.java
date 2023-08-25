package com.webline.socialmedia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.webline.socialmedia.config.JwtService;
import com.webline.socialmedia.entity.ErrorMessage;
import com.webline.socialmedia.entity.JwtRequest;
import com.webline.socialmedia.entity.JwtResponse;
import com.webline.socialmedia.entity.RegisterRequest;
import com.webline.socialmedia.entity.User;
import com.webline.socialmedia.entity.UserDto;
import com.webline.socialmedia.exception.UserExistException;
import com.webline.socialmedia.service.UserService;
import com.webline.socialmedia.service.ValidationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "User APIs")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ValidationService validationService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Operation(description = "Register API - role can only be 'ROLE_ADMIN' or 'ROLE_USER'")
	@PostMapping("/auth/register")
	public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request) throws UserExistException {

		//Validating registration input parameters
		String errorMessage = validationService.validateRegisterRequest(request);
		
		if (errorMessage != null && !errorMessage.isEmpty()) {
			JwtResponse jwtResponse = new JwtResponse(null, request.getUserName(), errorMessage);
			return new ResponseEntity<>(jwtResponse, HttpStatus.BAD_REQUEST);
		} else {
			//Checking if a user already exist with same user name
			User user = userService.checkUser(request.getUserName());
			User savedUser;
			if (user == null) {
				// Creating New User
				user = new User(request.getUserName(), request.getPassword(), request.getEmail(), request.getRole());
				savedUser = userService.addUser(user);

				if (savedUser != null) {
					//Creating Jwt and returning it in response
					String jwt = jwtService.generateToken(user);
					JwtResponse jwtResponse = new JwtResponse(jwt, user.getUserName(), null);
					return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
				} else {
					JwtResponse jwtResponse = new JwtResponse(null, request.getUserName(),
							"Error in registering user.");
					return new ResponseEntity<>(jwtResponse, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				JwtResponse jwtResponse = new JwtResponse(null, request.getUserName(),
						"User Already exist with same User Name");
				return new ResponseEntity<>(jwtResponse, HttpStatus.BAD_REQUEST);
			}
		}
	}

	@Operation(description = "Login API")
	@PostMapping("/auth/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) throws BadCredentialsException {

		//Validating login request
		String errorMessage = validationService.validateLoginRequest(request);

		if (errorMessage != null && !errorMessage.isEmpty()) {
			JwtResponse jwtResponse = new JwtResponse(null, request.getUserName(), errorMessage);
			return new ResponseEntity<>(jwtResponse, HttpStatus.BAD_REQUEST);
		}

		//Authenticating user name and password
		try {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
		}
		catch(Exception e) {
			JwtResponse jwtResponse = new JwtResponse(null, request.getUserName(),
					"User name or password is wrong.");
			return new ResponseEntity<>(jwtResponse, HttpStatus.BAD_REQUEST);
		}
		
		//Getting user with input username to generate token
		User user = userService.checkUser(request.getUserName());
		
		if (user == null) {
			JwtResponse jwtResponse = new JwtResponse(null, request.getUserName(),
					"User does not exist with this User Name");
			return new ResponseEntity<>(jwtResponse, HttpStatus.BAD_REQUEST);
		} else {
			//Generating jwt token and returning it in response
			String jwt = jwtService.generateToken(user);
			JwtResponse jwtResponse = new JwtResponse(jwt, user.getUserName(), null);
			return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
		}
	}

	@Operation(description = "Get all users")
	@GetMapping("/allUsers")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<List<UserDto>> getAllUsers(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) String pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) String pageSize,
			@RequestParam(value = "sortedBy", defaultValue = "USER_ID", required = false) String sortedBy,
			@RequestParam(value = "sortType", defaultValue = "ASC", required = false) String sortType,
			Authentication authentication) {
		
		//Getting users based on input parameters
		List<UserDto> users = userService.getAllUsers(pageNumber, pageSize, sortedBy, sortType);

		User user = (User) authentication.getPrincipal();

		//If user role ROLE_USER then he/she will not be able to see users' roles
		//Only users with role ROLE_ADMIN will be able see other users' roles 
		if (user.getAuthorities().toString().contains("ROLE_USER")) {
			users.forEach(userDto -> userDto.setRole(null));
		}

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@Operation(description = "Get users by ids")
	@GetMapping("/allUsersById")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<List<UserDto>> getAllUsersById(@RequestParam("id") List<Long> id) {
		
		//Getting users based on ids provided in the input array
		List<UserDto> users = userService.getAllUsersById(id);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
}
