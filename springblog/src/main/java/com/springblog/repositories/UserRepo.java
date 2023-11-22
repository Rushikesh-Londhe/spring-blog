package com.springblog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springblog.entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

	User findByEmail(String email);
User findByName(String name);
	
}
