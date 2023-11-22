package com.springblog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springblog.payloads.ApiResponse;
import com.springblog.payloads.CategoryDto;

import com.springblog.services.CategoryService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {
@Autowired
		private CategoryService cs;
	
//create
@PostMapping("/")
	
	public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto){
		
		CategoryDto createCategory = this.cs.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	
	//update
@PutMapping("/{id}")

public ResponseEntity<CategoryDto> update(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("id")Integer id){
	
	CategoryDto updatedCategory = this.cs.updateCategory(categoryDto,id);
	return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.CREATED);
}
	
	//delete
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> delete(@PathVariable("id")Integer id){
		
		this.cs.deleteCategory(id);
		return ResponseEntity.ok(new ApiResponse("category deleted successfully",true));
	}
	
	//get
	@GetMapping("/{id}")
public ResponseEntity<CategoryDto> getById(@PathVariable("id")Integer id){
		
		return ResponseEntity.ok(this.cs.getCategory(id));
		
	}
	
	
	//get all
@GetMapping("/")
public ResponseEntity<List<CategoryDto>> getAll(){
	return ResponseEntity.ok(this.cs.getCategories());
	
	
}
	
	
}
