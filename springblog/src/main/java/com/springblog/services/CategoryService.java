package com.springblog.services;

import java.util.List;

import com.springblog.payloads.CategoryDto;

public interface CategoryService {

	
	
	//create
	
	CategoryDto createCategory(CategoryDto categoryDto);
		
		
	//update
	 CategoryDto updateCategory(CategoryDto categoryDto,Integer id);
	
	//delete
	public void deleteCategory(Integer id);
	
	//get
	CategoryDto getCategory(Integer id);
	
	//get All
	 List<CategoryDto> getCategories();
}
