package com.springblog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springblog.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
