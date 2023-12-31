package com.springblog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springblog.entity.Comment;
import com.springblog.payloads.ApiResponse;
import com.springblog.payloads.CommentDto;
import com.springblog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService cs;
	@PostMapping("/posts/{postId}/{userId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment,@PathVariable Integer postId,@PathVariable Integer userId){
		
		CommentDto createComment = this.cs.createComment(comment,postId,userId);
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{commentId}/comments")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		
	this.cs.deleteComment(commentId);
		return new  ResponseEntity<ApiResponse>(new ApiResponse("comment deleted suceesfully",true),HttpStatus.OK);
	}
	
	
}
