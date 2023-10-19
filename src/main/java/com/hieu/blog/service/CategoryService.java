package com.hieu.blog.service;

import java.util.List;

import com.hieu.blog.payloads.CategoryDTO;

public interface CategoryService {

	// create
	public CategoryDTO createCategory(CategoryDTO categoryDTO);

    // update
	CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);

	// delete
	void deleteCategory(Integer categoryId);

	// get
	CategoryDTO getCategory(Integer categoryId);

	// get all
	List<CategoryDTO> getCategories();
}
