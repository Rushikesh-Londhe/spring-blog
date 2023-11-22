package com.springblog.services;

import java.util.List;

import com.springblog.entity.Post;
import com.springblog.payloads.PostDto;
import com.springblog.payloads.PostResponse;

public interface PostService {

	
	//create
	PostDto createPost(PostDto postDto,Integer id1,Integer id2);
	
	//update
	PostDto updatePost(PostDto postDto,Integer id);
	
	//delete
	void deletePost(Integer id);

	//get  all
	PostResponse getAllPost(Integer pageNo,Integer pageSize,String sortBy,String sortDir);
	
	//single
	PostDto getPostById(Integer id);
	
	//all post by category
	PostResponse getPostByCategory(Integer pageNo,Integer pageSize,Integer id);
	
	//all post by user
		List<PostDto> getPostByUser(Integer id);
		
		
		//search post
		List<PostDto> searchPost(String keyword);
}
