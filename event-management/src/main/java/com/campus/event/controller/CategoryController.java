package com.campus.event.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/categories")
@RestController
@Slf4j
public class CategoryController {
	
		@PostMapping 
		public String createCategory() {
			log.info("creteCategory() called");
			return "";
		}
		
		@GetMapping
		public String getAllCategories() {
			log.info("getAllCategories() called");
			return "";
		}
		
		@GetMapping("/{id}")
		public String getCategoryById(int id) {
			log.info("getCategory called with id : {} ",id);
			return "";
		}
		
		@PutMapping("/{id}")
		public String updateCategoryById(int id) {
			log.info("updated Category called with id : {} ",id);
			return "";
		}
		
		@DeleteMapping("/{id}")
		public String deleteCategoryById(int id) { 
			log.info("deleteCategory() called called with id : {} ",id);
			return "";
		}

}
