package com.hieu.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hieu.blog.entities.Category;
import com.hieu.blog.exceptions.ResourceNotFoundException;
import com.hieu.blog.payloads.CategoryDTO;
import com.hieu.blog.repositories.CategoryRepository;
import com.hieu.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	/*
	 * the method createCategory(CategoryDTO categoryDTO) is create category
	 */
	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category category = modelMapper.map(categoryDTO, Category.class);
		Category addCategory = categoryRepository.save(category);

		return modelMapper.map(addCategory, CategoryDTO.class);
	}

	/*
	 * the method updateCategory(CategoryDTO categoryDTO, Integer categoryId) is
	 * update category
	 */
	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
		Category category = categoryRepository.findById(categoryId)

				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		category.setCategoryTitle(categoryDTO.getCategoryTitle());
		category.setCategoryDescription(categoryDTO.getCategoryDescription());
		Category updatedCategory = categoryRepository.save(category);

		return modelMapper.map(updatedCategory, CategoryDTO.class);
	}

	/*
	 * the method deleteCategory(Integer categoryId) is delete category ,
	 * 
	 * find category allow category id in database, if exits then delete it
	 * 
	 * , if not found ,then return new ResourceNotFoundException
	 */
	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = categoryRepository.findById(categoryId)

				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		categoryRepository.delete(category);

	}
	/*
	 * the method getCategory(Integer categoryId) which get a category by category
	 * id ,
	 * 
	 * find category id has in database
	 * 
	 * if exits then convert from category object to category DTO using modelMapper
	 * and display ,
	 * 
	 * if not found , then return new ResourceNotFoundException include properties :
	 * Category , Category id , and variable caterogyId
	 */

	@Override
	public CategoryDTO getCategory(Integer categoryId) {
		Category category = categoryRepository.findById(categoryId)

				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		return modelMapper.map(category, CategoryDTO.class);
	}

	/*
	 * the method is ...
	 */
	@Override
	public List<CategoryDTO> getCategories() {
		
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDTO> catDtos = categories.stream()

				.map((cat) -> modelMapper.map(cat, CategoryDTO.class))

				.collect(Collectors.toList());
		return catDtos;
	}

}
