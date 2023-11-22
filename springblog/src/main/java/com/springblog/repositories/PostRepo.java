package com.springblog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springblog.entity.Category;
import com.springblog.entity.Post;
import com.springblog.entity.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	
	//List<Post> findByTitleContaining(String title);
	
	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key")String title);
	/*
	 * @Query("select p from Post p where lower(p.title) like concat('%', :keyword,'%')"
	 * ) public List<Post> searchPostHavingKeyword(@Param("keyword") String
	 * keyword);
	 */
	Page<Post> findByCategory(Category cat, Pageable p);
}
