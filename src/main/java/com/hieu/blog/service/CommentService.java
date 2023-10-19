package com.hieu.blog.service;

import com.hieu.blog.payloads.CommentDTO;

public interface CommentService {

	
	CommentDTO createComment(CommentDTO commentDTO, Integer postId);
	
	void  deleteComment(Integer commentId);
}
