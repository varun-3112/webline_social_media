package com.webline.socialmedia.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.webline.socialmedia.entity.User;
import com.webline.socialmedia.entity.UserDto;

@Repository
public interface UserRepository {

	List<UserDto> getAllUsers(String pageNumber, String pageSize, String sortedBy, String sortType);

	List<UserDto> getAllUsersById(List<Long> id);
	
	User findUserByUserName(String userName);

	User addUser(User user);

}
