package com.hieu.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hieu.blog.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
