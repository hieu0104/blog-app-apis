package com.hieu.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hieu.blog.payloads.ApiResponse;
import com.hieu.blog.payloads.CommentDTO;
import com.hieu.blog.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentService commentService;

//create comment
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(

			@RequestBody CommentDTO comment,

			@PathVariable Integer postId)

	{
		CommentDTO createComment = commentService.createComment(comment, postId);

		return new ResponseEntity<CommentDTO>(createComment, HttpStatus.CREATED);

	}

	// delete comment
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		commentService.deleteComment(commentId);

		return new ResponseEntity<ApiResponse>(

				new ApiResponse("Comment delete is successfully", true), HttpStatus.OK);

	}
}
