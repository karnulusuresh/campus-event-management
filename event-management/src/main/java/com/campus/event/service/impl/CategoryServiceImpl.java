package com.campus.event.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.campus.event.dto.CategoryDTO;
import com.campus.event.entity.Category;
import com.campus.event.exception.ResourceNotFoundException;
import com.campus.event.pojo.CategoryRequest; 
import com.campus.event.repository.CategoryRepository;
import com.campus.event.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor 
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    
    @Override
    public CategoryDTO createCategory(CategoryRequest request) {
    	log.info("createCategory() was called....");
    	
        if (categoryRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Category already exists with name: " + request.getName());
        }
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        
        Category saved = categoryRepository.save(category); 
        CategoryDTO response = mapToDTO(saved); 
        log.info("category created successfully with id : {}",response.getId());
        return response; 
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
    	log.info("called getCategoryById() with id : {}",id);
        Category response = categoryRepository.findById(id) 
                .orElseThrow(() -> new ResourceNotFoundException("Category"));
        log.info("recieved response as : {}",response);
        return mapToDTO(response);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
    	log.info("called getAllCategories().....");
        List<CategoryDTO> response =  categoryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList(); 
        log.info("retrieved all categories as : {}",response); 
        return response;
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
    	log.info("called updateCategory with id : {}",id);
        Category response = categoryRepository.findById(id) 
                .orElseThrow(() -> new ResourceNotFoundException("Category"));

        response.setName(dto.getName());
        response.setDescription(dto.getDescription());
        Category updated = categoryRepository.save(response);
        log.info("recieved response as : {}",response);
        return mapToDTO(updated);
    }

    @Override
    public void deleteCategory(Long id) {
    	log.info("deleting category with id : {}",id);
        Category response = categoryRepository.findById(id) 
                .orElseThrow(() -> new ResourceNotFoundException("Category"));
        categoryRepository.delete(response); 
        log.info("deleted category successfully..."); 
    }

    private CategoryDTO mapToDTO(Category category) {
        return new CategoryDTO(category.getCategoryId(), category.getName(), category.getDescription());
    }
}
