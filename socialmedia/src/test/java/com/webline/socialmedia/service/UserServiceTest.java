package com.webline.socialmedia.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.webline.socialmedia.entity.User;
import com.webline.socialmedia.repository.UserRepository;

@SpringBootTest
class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	@BeforeEach
	void setUp() {
		//Setting demo object
		User user=new User("varun", "varun", "varun@gmail.com", "ROLE_ADMIN");
		
		//Returns demo object when repository method is called
		Mockito.when(userRepository.findUserByUserName("varun"))
		.thenReturn(user);
	}
	
	
	//Unit test case to get user against user name
	@Test
	void testCheckUser() {
		String userName="varun";
		User user=userService.checkUser(userName);
		
		assertEquals(userName, user.getUserName());
	}

}
