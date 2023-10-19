package com.hieu.blog.service;

import java.util.List;

import com.hieu.blog.payloads.PostDTO;
import com.hieu.blog.payloads.PostResponse;

/**
 *
 * @author LeChiHieu
 * 
 *  the class is service player of Post object , contains methods Post 
 *
 */
public interface PostService {

	// create
	PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

	// update
	PostDTO updatePost(PostDTO postDTO, Integer postId);

	// delete
	void deletePost(Integer postId);

	//get all posts ,sort pagination 
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

	// get single post
	PostDTO getPostById(Integer postId);

	// get all posts by category
	List<PostDTO> getPostsByCategory(Integer categoryId);

	// get all posts by user
	List<PostDTO> getPostsByUser(Integer userId);

	// search posts
	List<PostDTO> searchPosts(String keyword);
	
	
}
