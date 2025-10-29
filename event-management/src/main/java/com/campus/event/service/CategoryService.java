package com.campus.event.service;

import com.campus.event.dto.CategoryDTO;
import com.campus.event.pojo.CategoryRequest;

import java.util.List;

public interface CategoryService {
	
    CategoryDTO createCategory(CategoryRequest request);
    
    CategoryDTO getCategoryById(Long id);
    
    List<CategoryDTO> getAllCategories();
    
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    
    void deleteCategory(Long id);
}
