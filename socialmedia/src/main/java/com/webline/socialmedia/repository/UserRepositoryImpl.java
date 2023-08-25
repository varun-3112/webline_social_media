package com.webline.socialmedia.repository;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.webline.socialmedia.entity.User;
import com.webline.socialmedia.entity.UserDto;

@Repository
public class UserRepositoryImpl implements UserRepository{

	@Autowired
	private Environment env;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<UserDto> getAllUsers(String pageNumber, String pageSize, String sortedBy, String sortType) {
		String GET_ALL_USERS=env.getProperty("GET_ALL_USERS")+" "+sortedBy+" "+sortType+" "+env.getProperty("PAGINATION_QUERY");
		return jdbcTemplate.query(GET_ALL_USERS, (rs,rowNum) -> {
			return new UserDto(rs.getLong("user_id"),rs.getString("user_name"),rs.getString("email"),rs.getString("role"),null);
		},Integer.parseInt(pageSize),Integer.parseInt(pageNumber));
	}

	@Override
	public List<UserDto> getAllUsersById(List<Long> id) {
		
		String inSql=String.join(",", Collections.nCopies(id.size(),"?"));
		
		String GET_ALL_USERS_BY_ID=String.format(env.getProperty("GET_ALL_USERS_BY_ID"), inSql);
		
		return jdbcTemplate.query(GET_ALL_USERS_BY_ID, (rs,rowNum) -> {
			return new UserDto(rs.getLong("user_id"),rs.getString("user_name"),rs.getString("email"),rs.getString("role"),null);
		},id.toArray());
	}

	@Override
	public User findUserByUserName(String userName) {
		User user=null;
		try {
		user=jdbcTemplate.queryForObject(env.getProperty("GET_USER_BY_USERNAME"), (rs,rowNum) -> {
			return new User(rs.getLong("user_id"),rs.getString("user_name"),rs.getString("password"),rs.getString("email"),rs.getString("role"));
		}, userName);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		try {
			jdbcTemplate.update(env.getProperty("ADD_NEW_USER"),user.getUserName(),user.getPassword(),user.getEmail(),user.getRole());
		}catch (Exception e) {
			return null;
		}
		return user;
	}
}
