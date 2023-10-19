package com.hieu.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hieu.blog.config.AppConstants;
import com.hieu.blog.payloads.ApiResponse;
import com.hieu.blog.payloads.PostDTO;
import com.hieu.blog.payloads.PostResponse;
import com.hieu.blog.service.FileService;
import com.hieu.blog.service.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController {


	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	// create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @PathVariable Integer userId,
			@PathVariable Integer categoryId

	) {
		PostDTO createPost = postService.createPost(postDTO, userId, categoryId);

		return new ResponseEntity<PostDTO>(createPost, HttpStatus.CREATED);

	}

	// update
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> updatePost(

			@RequestBody PostDTO postDTO,

			@PathVariable Integer postId) {

		PostDTO updatePost = postService.updatePost(postDTO, postId);

		return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);

	}

	// delete
	@DeleteMapping("posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {

		postService.deletePost(postId);
		return new ApiResponse("Post is successfully deleted !!", true);

	}

	// get all posts

	/**
	 * the controller method which get all post , include parameters:
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * 
	 * @return PostRespone contains PostDTO data , page number , page size, sort
	 *         allow conditions, sortDir
	 */

	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(

			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir

	) {

		PostResponse postResponse = postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	// get post details by id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId) {

		PostDTO postDTO = postService.getPostById(postId);

		return new ResponseEntity<PostDTO>(postDTO, HttpStatus.OK);

	}

	// get post by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByUser(

			@PathVariable Integer userId

	) {
		List<PostDTO> posts = postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}

	// get post by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDTO>> getCategory(

			@PathVariable Integer categoryId) {

		List<PostDTO> posts = postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}

	// search post
	@GetMapping("/posts/search/{keyWords}")
	public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable String keyWords) {

		List<PostDTO> result = postService.searchPosts(keyWords);
		return new ResponseEntity<List<PostDTO>>(result, HttpStatus.OK);

	}

	// post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId

	) throws IOException {

		PostDTO postDTO = postService.getPostById(postId);
		String fileImage = fileService.uploadImage(path, image);
		postDTO.setImageName(fileImage);
		PostDTO updatePost = postService.updatePost(postDTO, postId);

		return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);

	}

	// save files
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,

			HttpServletResponse response) throws IOException {
		
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		org.springframework.util.StreamUtils.copy(resource, response.getOutputStream());
	}
}
