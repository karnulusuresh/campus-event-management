package com.campus.event.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campus.event.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> { 
	
	boolean existsByName(String name); 
	
	Optional<Category> findByName(String name);
}