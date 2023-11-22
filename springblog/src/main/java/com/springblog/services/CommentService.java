package com.springblog.services;


import com.springblog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto comment,Integer postId,Integer userId);
	void deleteComment(Integer commentId);
}
