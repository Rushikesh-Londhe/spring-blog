package com.springblog.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.springblog.payloads.UserDto;

public interface UserService {

	UserDto registerNewUser(UserDto user);
	
	UserDto createUser(UserDto user);
	UserDto update(UserDto user,Integer id);
	UserDto getUserById(Integer id);
	List<UserDto> getAllUsers();
	void deleteUser(Integer id);

	UserDetails loadUserByUsername(String username);
	
	
	//String createUser(UserDto user);
}
