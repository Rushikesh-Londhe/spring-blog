package com.springblog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CategoryDto {
	private Integer id;
	@NotEmpty
	@Size(min=4,message="title must be greater than 4 characters")
	private String title;
	
	@NotEmpty
	@Size(min=4,message="Description must be greater than 4 characters")
	private String description;
	public CategoryDto() {
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
