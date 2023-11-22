package com.springblog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.springblog.entity.Comment;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public class PostDto {

	
	
	
	private Integer id;
	@NotEmpty
	@Size(min=4,message="Title must be greater than 4 characters")
	private String title;
	@NotEmpty
	@Size(min=4,message="Title must be greater than 4 characters")
	private String content;
	private String imgName;
	private Date date;
	
	private CategoryDto category;
	private UserDto user;
	private Set<CommentDto>comments=new HashSet<>();
	public PostDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public CategoryDto getCategory() {
		return category;
	}
	public void setCategory(CategoryDto category) {
		this.category = category;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}


	public Set<CommentDto> getComments() {
		return comments;
	}


	public void setComments(Set<CommentDto> comments) {
		this.comments = comments;
	}
	

	
	
	
}
