package com.hieu.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hieu.blog.entities.Category;
import com.hieu.blog.entities.Post;
import com.hieu.blog.entities.User;
import com.hieu.blog.exceptions.ResourceNotFoundException;
import com.hieu.blog.payloads.PostDTO;
import com.hieu.blog.payloads.PostResponse;
import com.hieu.blog.repositories.CategoryRepository;
import com.hieu.blog.repositories.PostRepository;
import com.hieu.blog.repositories.UserRepository;
import com.hieu.blog.service.PostService;

/*the service player
*/

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	/*
	 * the method createPost(PostDTO postDTO, Integer userId, Integer categoryId)
	 * 
	 * which create post base in user id , category id
	 * 
	 * find user id , else return new message
	 */
	@Override
	public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {

		User user = userRepository.findById(userId).orElseThrow(

				() -> new ResourceNotFoundException("User", "User id ", userId)

		);

		/*
		 * find category id , if not found , return new msg
		 */
		Category category = categoryRepository.findById(categoryId)

				.orElseThrow(

						() -> new ResourceNotFoundException("Category", "Category id ", categoryId)

				);

		/*
		 * generate post include properties: image , addDate, user, category
		 */
		Post post = modelMapper.map(postDTO, Post.class);

		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = postRepository.save(post);

		return modelMapper.map(newPost, PostDTO.class);// why convert ?
	}

	/*
	 * the method PostDTO updatePost(PostDTO postDTO, Integer postId) which update
	 * post,
	 * 
	 * find post by post id , set date then save and convert post object to post dto
	 */
	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer postId) {
		
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setImageName(postDTO.getImageName());

		Post updatePost = postRepository.save(post);

		return modelMapper.map(updatePost, PostDTO.class);
	}

	/*
	 * the method void deletePost(Integer postId) which delete
	 * 
	 */
	@Override
	public void deletePost(Integer postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		postRepository.delete(post);
	}

	/*
	 * the method List<PostDTO> getAllPost() which get all posts have in database
	 * 
	 * include variables : 
	 * 
	 * pageNumber is number of page 
	 * 
	 * pageSize is size which page have 
	 * 
	 * sortBy is sort allow condition , 
	 * 
	 * sortDir is sort ...
	 * 
	 * return list post dto (data transfer object )
	 */
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		/*
		 * solution 1: It uses the ternary operator (toán tử a ngôi ) which is a
		 * shorthand way of writing an if-else statement.
		 */

		Sort sort = (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

		/*
		 * solution 2: writing an if-else statement.
		 * 
		 * if (sortDir.equalsIgnoreCase("asc")) {
		 * 
		 * sort = Sort.by(sortBy).ascending();
		 * 
		 * } else {
		 * 
		 * sort = Sort.by(sortBy).descending(); }
		 */

		// pagination, sort 
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> pagePost = postRepository.findAll(p);

		List<Post> allPosts = pagePost.getContent();
        //using Stream API convert data form post object  in entities to post DTO 
		List<PostDTO> postDTOs = allPosts.stream().map((post) -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
        //set new data for post 
		postResponse.setContent(postDTOs);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPage(pagePost.getTotalPages());

		return postResponse;
	}

	/*
	 * the method PostDTO getPostById(Integer postId) which get post by post id
	 * 
	 * find post id and convert to DTO(data transfers object , a design pattern
	 * 
	 * if not exits return ResourceNotFoundException("Post", "Post id", postId)
	 */
	@Override
	public PostDTO getPostById(Integer postId) {

		Post post = postRepository.findById(postId).orElseThrow(

				() -> new ResourceNotFoundException("Post", "Post id", postId)

		);

		return modelMapper.map(post, PostDTO.class);
	}

	/*
	 * the List<PostDTO> getPostsByCategory(Integer categoryId) which get posts by
	 * category ,
	 * 
	 * ....
	 */
	@Override
	public List<PostDTO> getPostsByCategory(Integer categoryId) {
		Category cat = categoryRepository.findById(categoryId).orElseThrow(

				() -> new ResourceNotFoundException("Category", "Category id", categoryId)

		);

		List<Post> posts = postRepository.findByCategory(cat);
		List<PostDTO> postDTOs = posts.stream().map((post) -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());

		return postDTOs;
	}

	/*
	 * the method List<PostDTO> getPostsByUser(Integer userId) which get post by
	 * user ,using Stream API convert post object to convert post object to post DTO
	 */
	@Override
	public List<PostDTO> getPostsByUser(Integer userId) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id ", userId)

				);
		List<Post> posts = postRepository.findByUser(user);

		List<PostDTO> postDTOs = posts.stream().map((post) -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
		return postDTOs;

	}

	/*
	 * the method List<Post> searchPosts(String keyword) which search posts
	 * 
	 * find post by keyword in database , using Stream API convert post object to
	 * post DTO
	 * 
	 * return PostDTO data 
	 */

	@Override
	public List<PostDTO> searchPosts(String keyword) {

		List<Post> posts = postRepository.findByTitleContaining("%" + keyword + "%");

		List<PostDTO> postDTOs = posts.stream().map((post) -> modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());

		return postDTOs;
	}

}
