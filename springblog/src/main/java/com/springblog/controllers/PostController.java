package com.springblog.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springblog.config.AppConstants;
import com.springblog.payloads.ApiResponse;
import com.springblog.payloads.PostDto;
import com.springblog.payloads.PostResponse;
import com.springblog.services.FileService;
import com.springblog.services.PostService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostController {
@Autowired
	private PostService ps;

@Value("${project.image}")
private String path;

@Autowired
private FileService fs;
	
	@PostMapping("/user/{userId}/category/{catId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,@PathVariable("userId")Integer userId,@PathVariable("catId")Integer catId){
		
		PostDto createPost = this.ps.createPost(postDto, userId, catId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	//get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
		
		List<PostDto> posts = this.ps.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		
	}
	
	//get by category 
	@GetMapping("/category/{catId}/posts")
		public ResponseEntity<PostResponse> getPostByCategory(@RequestParam(value="pageNo",defaultValue=AppConstants.PAGE_NO,required=false)Integer pageNo,
				@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false)Integer pageSize,
				@PathVariable Integer catId){
			
			PostResponse pr= this.ps.getPostByCategory(pageNo,pageSize,catId);
			return new ResponseEntity<PostResponse>(pr,HttpStatus.OK);
			
		}
		
		 //get post by id
	
	@GetMapping("/{postId}/posts")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		
		PostDto postById = this.ps.getPostById(postId);
		return new ResponseEntity<PostDto>(postById,HttpStatus.OK);
	}
	//get all
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value="pageNo",defaultValue=AppConstants.PAGE_NO,required=false)Integer pageNo,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false)String sortDir){
		
	PostResponse allPosts = this.ps.getAllPost(pageNo,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(allPosts,HttpStatus.OK);
	}
	
	//delete post
	@DeleteMapping("/{postId}/posts")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		
	this.ps.deletePost(postId);
	return new ApiResponse("Post is successfully deleted!!",true);
	}
	
	//update post
		@PutMapping("/{postId}/posts")
		public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
			
		PostDto updatePost = this.ps.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		}
		
		//searching
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords")String keywords){
		List<PostDto> searchPost = this.ps.searchPost(keywords);
		return new ResponseEntity<List<PostDto>>(searchPost,HttpStatus.OK);
	}
	
	//post image upload
//	@PostMapping("/posts/image/upload/{id}")
//	public ResponseEntity<PostDto> uploadPostImage(
//			@RequestParam("image")MultipartFile image,@PathVariable Integer id) throws IOException{
//		PostDto postDto = this.ps.getPostById(id);
//		String fileName = this.fs.uploadImage(path, image);
//		
//		postDto.setImgName(fileName);
//		
//		PostDto updatePost = this.ps.updatePost(postDto, id);
//		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
//	}
	@PostMapping("/posts/image/upload/{id}")
	public ResponseEntity<PostDto> uploadPostImage(
	    @RequestParam("image") MultipartFile image, @PathVariable Integer id) throws IOException {
	    PostDto postDto = this.ps.getPostById(id);

	    String fileName = this.fs.uploadImage(path, image);
	    
	    // Update the file name in the PostDto
	    postDto.setImgName(fileName);
	    
	    // Save the updated PostDto with the new file name
	    PostDto updatedPost = this.ps.updatePost(postDto, id);

	    return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
	
	//method to serve files
	@GetMapping(value="/posts/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName")String imageName,HttpServletResponse response) throws IOException {
		InputStream resource =this.fs.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
		
}
