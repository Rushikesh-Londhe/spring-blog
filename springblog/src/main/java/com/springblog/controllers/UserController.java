package com.springblog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springblog.entity.User;
import com.springblog.exceptions.UserAlreadyExistException;
import com.springblog.payloads.ApiResponse;
import com.springblog.payloads.UserDto;
import com.springblog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
@Autowired
	private UserService us;


//	//POST-create user
//	@PostMapping("/")
//	public ResponseEntity<UserDto> createUser( @RequestBody UserDto userDto){
//		
//		UserDto createUserDto = this.us.createUser(userDto);
//		
//		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
//		
//		
//	}


//POST-create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createUserDto = this.us.createUser(userDto);
		return new ResponseEntity<UserDto>(createUserDto, HttpStatus.CREATED);
	}
	
	//PUT-update
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto,@PathVariable("id") Integer id){
	
		UserDto updatedUser=this.us.update(userDto, id);
		return new ResponseEntity<>(updatedUser,HttpStatus.OK);
	}

	//admin
	//DELETE-delete
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> delete(@PathVariable("id") Integer id){
		
	this.us.deleteUser(id);
	return ResponseEntity.ok(new ApiResponse("user deleted successfully",true));
	//return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//GET-get
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getById(@PathVariable("id")Integer id){
		
		return ResponseEntity.ok(this.us.getUserById(id));
		
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAll(){
		return ResponseEntity.ok(this.us.getAllUsers());
		
		
	}
}
