package com.springblog.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springblog.entity.Comment;
import com.springblog.entity.Post;
import com.springblog.entity.User;
import com.springblog.exceptions.ResourceNotFoundException;
import com.springblog.payloads.CommentDto;
import com.springblog.repositories.CommentRepo;
import com.springblog.repositories.PostRepo;
import com.springblog.repositories.UserRepo;
import com.springblog.services.CommentService;
@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepo cr;
	@Autowired
	private PostRepo pr;
	@Autowired
	private UserRepo ur;
	@Autowired
	private ModelMapper mm;
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId,Integer userId) {
		Post post=this.pr.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
		User user=this.ur.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","user id",userId));
		Comment comment = this.mm.map(commentDto ,Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		Comment save = this.cr.save(comment);
		return this.mm.map(save, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
	Comment com=this.cr.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","comment id",commentId));
	this.cr.delete(com);
		
	}

}
