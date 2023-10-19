package com.hieu.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hieu.blog.entities.Comment;
import com.hieu.blog.entities.Post;
import com.hieu.blog.exceptions.ResourceNotFoundException;
import com.hieu.blog.payloads.CommentDTO;
import com.hieu.blog.repositories.CommentRepository;
import com.hieu.blog.repositories.PostRepository;
import com.hieu.blog.service.CommentService;

/**
 * @author LeChiHieu
 *
 */

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * the method CommentDTO createComment(CommentDTO commentDTO, Integer postId)
	 * which create comment
	 */
	@Override
	public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id ", postId)

				);
		Comment comment = modelMapper.map(commentDTO, Comment.class);

		comment.setPost(post);

		Comment saveComment = commentRepository.save(comment);

		return modelMapper.map(saveComment, CommentDTO.class);
	}

	/**
	 * the method void deleteComment(Integer commentId) which delete comment
	 */
	@Override
	public void deleteComment(Integer commentId) {

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId)

				);
		commentRepository.delete(comment);

	}

}
