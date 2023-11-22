package com.springblog.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springblog.entity.Category;
import com.springblog.exceptions.ResourceNotFoundException;
import com.springblog.payloads.CategoryDto;
import com.springblog.repositories.CategoryRepo;
import com.springblog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
@Autowired
	private CategoryRepo cr;

@Autowired
private ModelMapper mm;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		 Category cat = this.mm.map(categoryDto, Category.class);
		 Category addedCat=this.cr.save(cat);
		 
		
		return this.mm.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
		
		Category cat = this.cr.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","id",id));
		
		 cat.setTitle(categoryDto.getTitle());
		 cat.setDescription(categoryDto.getDescription());
		
		 Category updatedCat = this.cr.save(cat);
		 
		 return this.mm.map(updatedCat, CategoryDto.class);
		
	}

	@Override
	public void deleteCategory(Integer id) {
		Category cat = this.cr.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","id",id));
		this.cr.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer id) {
		Category cat = this.cr.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","id",id));
		 return this.mm.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories = this.cr.findAll();
		
		List<CategoryDto> catDtos = categories.stream().map((cat)->this.mm.map(cat,CategoryDto.class)).collect(Collectors.toList());
		
		return catDtos;
	}

}
