package com.springblog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.springblog.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDto {

	
	
	private int id;
	
	@NotEmpty
	@Size(min=4,message="Name must be greater than 4 characters")
	private String name;
	
	@Email(message="Email address is not valid!!")
	private String email;
	
	@NotEmpty
	@Size(min=3,max=10,message="please enter password betwwen 3 to 10 characters")
	private String password;
	@NotEmpty
	
	private String about;
	
	private Set<RoleDto> roles=new HashSet<>();
	//private Set<CommentDto>comments=new HashSet<>();
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
public UserDto(int id, @NotEmpty @Size(min = 4, message = "Name must be greater than 4 characters") String name,
			@Email(message = "Email address is not valid!!") String email,
			@NotEmpty @Size(min = 3, max = 10, message = "please enter password betwwen 3 to 10 characters") String password,
			@NotEmpty String about, Set<RoleDto> roles) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
		this.roles = roles;
	}
//	public Set<CommentDto> getComments() {
//		return comments;
//	}
//	public void setComments(Set<CommentDto> comments) {
//		this.comments = comments;
//	}
	public Set<RoleDto> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleDto> roles) {
		this.roles = roles;
	}
	
	
}
