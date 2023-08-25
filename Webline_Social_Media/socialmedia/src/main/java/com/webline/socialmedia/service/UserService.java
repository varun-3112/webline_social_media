package com.webline.socialmedia.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.webline.socialmedia.entity.User;
import com.webline.socialmedia.entity.UserDto;

@Service
public interface UserService {

	List<UserDto> getAllUsers(String pageNumber, String pageSize, String sortedBy, String sortType);

	List<UserDto> getAllUsersById(List<Long> id);

	User checkUser(String userName);

	User addUser(User user);

}
