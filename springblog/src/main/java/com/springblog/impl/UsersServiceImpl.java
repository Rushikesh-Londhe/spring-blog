package com.springblog.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springblog.config.AppConstants;
import com.springblog.entity.Role;
import com.springblog.entity.User;
import com.springblog.exceptions.ResourceNotFoundException;
import com.springblog.exceptions.UserAlreadyExistException;
import com.springblog.payloads.UserDto;
import com.springblog.repositories.RoleRepo;
import com.springblog.repositories.UserRepo;
import com.springblog.services.UserService;
@Service
public class UsersServiceImpl implements UserService {
@Autowired
	private UserRepo ur;
@Autowired
private PasswordEncoder passEncoder;

@Autowired
private ModelMapper modelMapper;

@Autowired
private RoleRepo rr;
/*
 * @Override public String createUser(UserDto userDto) { User findByEmail =
 * ur.findByEmail(userDto.getEmail()); if (findByEmail== null) { User
 * user=this.dtoToUser(userDto); User savedUser = this.ur.save(user); return
 * "User Added Successfully"; }else { throw new
 * UserAlreadyExistException("User Already Exist"); }
 * 
 * }
 */
@Override
public UserDto createUser(UserDto userDto) {
	User user = this.dtoToUser(userDto);
	User savedUser = this.ur.save(user);
	return this.userToDto(savedUser);
}
	@Override
	public UserDto update(UserDto userDto, Integer id) {
		User user=this.ur.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		User updatedUser = this.ur.save(user);
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer id) {
		User user=this.ur.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users = this.ur.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer id) {
		User user=this.ur.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
		this.ur.delete(user);
	}
	
	
	//conversion
	public User dtoToUser(UserDto userDto) {
		
		User user=this.modelMapper.map(userDto,User.class);
		/*
		 * user.setId(userDto.getId()); user.setName(userDto.getName());
		 * user.setEmail(userDto.getEmail()); user.setAbout(userDto.getAbout());
		 * user.setPassword(userDto.getPassword());
		 */
		
		return user;
		
	}
	public UserDto userToDto(User user) {
		
		UserDto userDto=this.modelMapper.map(user,UserDto.class);
		/*
		 * userDto.setId(user.getId()); userDto.setName(user.getName());
		 * userDto.setEmail(user.getEmail()); userDto.setAbout(user.getAbout());
		 * userDto.setPassword(user.getPassword());
		 */
		return userDto;
		
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
	
		User user = this.modelMapper.map(userDto,User.class);
		
		//encoded password
		user.setPassword(this.passEncoder.encode(user.getPassword()));
		
		//roles
		Role role = this.rr.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		User save = this.ur.save(user);
		return this.modelMapper.map(save,UserDto.class);
	}
	@Override
	public UserDetails loadUserByUsername(String username) {
		return this.ur.findByName(username);
	}

}
