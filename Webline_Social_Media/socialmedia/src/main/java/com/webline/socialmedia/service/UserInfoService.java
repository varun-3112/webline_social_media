package com.webline.socialmedia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.webline.socialmedia.entity.User;
import com.webline.socialmedia.repository.UserRepository;

@Service
public class UserInfoService implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public User loadUserByUsername(String userName){
		return repository.findUserByUserName(userName);
	}
}
