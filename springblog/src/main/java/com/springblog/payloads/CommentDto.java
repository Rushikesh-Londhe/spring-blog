package com.springblog.payloads;

import java.util.HashSet;
import java.util.Set;

public class CommentDto {
	
		private int id;

	private String content;

	private Set<UserDto>user=new HashSet<>();
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<UserDto> getUser() {
		return user;
	}

	public void setUser(Set<UserDto> user) {
		this.user = user;
	}

	

	

	
}
