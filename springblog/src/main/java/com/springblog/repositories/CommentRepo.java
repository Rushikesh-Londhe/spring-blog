package com.springblog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springblog.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer> {

}
