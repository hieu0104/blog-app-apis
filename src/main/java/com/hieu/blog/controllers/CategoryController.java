package com.hieu.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hieu.blog.payloads.ApiResponse;
import com.hieu.blog.payloads.CategoryDTO;
import com.hieu.blog.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// create
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {

		CategoryDTO createCategory = categoryService.createCategory(categoryDTO);
		return new ResponseEntity<CategoryDTO>(createCategory, HttpStatus.CREATED);

	}

	// update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDTO> updateCategory(

			@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Integer catId) {

		CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO, catId);
		return new ResponseEntity<CategoryDTO>(updatedCategory, HttpStatus.OK);

	}

	// delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {

		categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(

				new ApiResponse("Category is deleted successfully !!", true), HttpStatus.OK);

	}

	// get
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer catId) {

		CategoryDTO categoryDTO = categoryService.getCategory(catId);
		return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);

	}

	// get all
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getCategories() {

		List<CategoryDTO> categories = categoryService.getCategories();
		return ResponseEntity.ok(categories);

	}

}
