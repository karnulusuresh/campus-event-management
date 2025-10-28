package com.campus.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campus.event.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> { }