package com.webline.socialmedia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webline.socialmedia.entity.User;
import com.webline.socialmedia.entity.UserDto;
import com.webline.socialmedia.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public List<UserDto> getAllUsers(String pageNumber, String pageSize, String sortedBy, String sortType) {
		return userRepository.getAllUsers(pageNumber,pageSize,sortedBy,sortType);
	}

	@Override
	public List<UserDto> getAllUsersById(List<Long> id) {
		return userRepository.getAllUsersById(id);
	}

	@Override
	public User checkUser(String userName) {
		// TODO Auto-generated method stub
		return userRepository.findUserByUserName(userName);
	}

	@Override
	public User addUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.addUser(user);
	}
	

}
