package com.springblog.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springblog.entity.Category;
import com.springblog.entity.Post;
import com.springblog.entity.User;
import com.springblog.exceptions.ResourceNotFoundException;
import com.springblog.payloads.PostDto;
import com.springblog.payloads.PostResponse;
import com.springblog.repositories.CategoryRepo;
import com.springblog.repositories.PostRepo;
import com.springblog.repositories.UserRepo;
import com.springblog.services.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepo pr;
	
	@Autowired
	private ModelMapper mm;
	
	@Autowired
	private UserRepo ur;
	
	@Autowired
	private CategoryRepo cr;
	@Override
	
	public PostDto createPost(PostDto postDto,Integer userId,Integer catId) {
		
		User user=this.ur.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User id", userId));
		Category cat=this.cr.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","Cat id", catId));
		
	Post post = this.mm.map(postDto,Post.class);
	post.setImgName(postDto.getImgName());
	post.setDate(new Date());
	post.setUser(user);
	post.setCategory(cat);
	
	Post newPost = this.pr.save(post);
	
	
		return this.mm.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer id) {
		Post post = this.pr.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "post id", id));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		Post updatedPost= this.pr.save(post);
		
		return this.mm.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer id) {
		Post post = this.pr.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "post id", id));
		this.pr.delete(post);
		
	}

	@Override
	public PostResponse getAllPost(Integer pageNo,Integer pageSize,String sortBy,String sortDir) {
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort=Sort.by(sortBy).ascending();
		}
		else {
			sort=Sort.by(sortBy).descending();
		}
		Pageable p=PageRequest.of(pageNo, pageSize,sort);
 Page<Post> pagePost = this.pr.findAll(p);
 List<Post> posts = pagePost.getContent();
	List<PostDto> postDtos = posts.stream().map((post)->this.mm.map(post, PostDto.class)).collect(Collectors.toList());
	PostResponse pr=new PostResponse();
	pr.setContent(postDtos);
	pr.setPageNo(pagePost.getNumber());
	pr.setPageSize(pagePost.getSize());
	pr.setTotalElements(pagePost.getTotalElements());
	pr.setTotalPages(pagePost.getTotalPages());
	pr.setLastPage(pagePost.isLast());
		return pr;
	}

	@Override
	public PostDto getPostById(Integer id) {
		
		  Post posts = this.pr.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "post id", id));
		  
		  return this.mm.map(posts, PostDto.class);
		 
		
	}

	@Override
	public PostResponse getPostByCategory(Integer pageNo,Integer pageSize,Integer catId) {
		Pageable p=PageRequest.of(pageNo, pageSize);
		
		Category cat=this.cr.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","Cat id", catId));
	  Page<Post> pagePosts = this.pr.findByCategory(cat,p);

	  List<Post> posts = pagePosts.getContent();
		List<PostDto> postDtos = posts.stream().map((post)->this.mm.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse pr=new PostResponse();
		pr.setContent(postDtos);
		pr.setPageNo(pagePosts.getNumber());
		pr.setPageSize(pagePosts.getSize());
		pr.setTotalElements(pagePosts.getTotalElements());
		pr.setTotalPages(pagePosts.getTotalPages());
		pr.setLastPage(pagePosts.isLast());
			return pr;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user=this.ur.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User id", userId));
		List<Post> posts = this.pr.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post)->this.mm.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
		
		
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
	
		//List<Post> posts = this.pr.findByTitleContaining(keyword);
		List<Post> posts = this.pr.searchByTitle("%"+keyword+"%");
		List<PostDto> postDtos = posts.stream().map((post)->this.mm.map(posts, PostDto.class)).collect(Collectors.toList());
		return postDtos;
		
		
	}

	
	
}
