package com.campus.event.controller;

import com.campus.event.dto.CategoryDTO;
import com.campus.event.pojo.CategoryRequest;
import com.campus.event.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryDTO createCategory(@RequestBody CategoryRequest request) {
    	log.info("Category creation was initiated in controller....");
    	CategoryDTO response = categoryService.createCategory(request);
    	log.info("retrieved response from createCategory as: {}",response);
        return response;
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Long id) {
    	log.info("getCategory() was initiated in controller with id : {}",id);
    	CategoryDTO response = categoryService.getCategoryById(id);
    	log.info("response recieved successfully...");
        return response;
        
    }

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
    	log.info("getAllCategories() was called in controller");
        List<CategoryDTO> response =categoryService.getAllCategories();
        log.info("recieved response from getAllCategopries successfully...");
        return response; 
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable Long id, @RequestBody CategoryDTO dto) {
    	log.info("updating category of id : {}",id);
        CategoryDTO response = categoryService.updateCategory(id, dto);
        log.info("updated successfully......"); 
        return response;
    }

    @DeleteMapping("/{id}")
    public String  deleteCategory(@PathVariable Long id) {
    	log.info("deleting respponse...");
        categoryService.deleteCategory(id);
        log.info("Category deleted successfully");
        return "Category deleted successfully";
    }
}
