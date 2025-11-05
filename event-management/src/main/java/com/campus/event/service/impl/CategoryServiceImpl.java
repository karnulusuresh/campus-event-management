package com.campus.event.service.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.campus.event.dto.CategoryDTO;
import com.campus.event.entity.Category;
import com.campus.event.exception.BadRequestException;
import com.campus.event.exception.ConflictException;
import com.campus.event.exception.GeneralServiceException;
import com.campus.event.exception.ResourceNotFoundException;
import com.campus.event.pojo.CategoryRequest;
import com.campus.event.repository.CategoryRepository;
import com.campus.event.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    // --------------------------------------------------------
    // 1. Create Category
    // --------------------------------------------------------
    @Override
    public CategoryDTO createCategory(CategoryRequest request) {
        try {
            log.info("Creating category with name: {}", request.getName());

            if (request.getName() == null || request.getName().trim().isEmpty()) {
                throw new BadRequestException("Category name cannot be empty.");
            }

            if (categoryRepository.existsByName(request.getName())) {
                throw new ConflictException("Category already exists with name: " + request.getName());
            }

            Category category = new Category();
            category.setName(request.getName().trim());
            category.setDescription(request.getDescription());

            Category saved = categoryRepository.save(category);
            CategoryDTO response = mapToDTO(saved);

            log.info("Category created successfully with ID: {}", response.getId());
            return response;

        } catch (BadRequestException | ConflictException ex) {
            throw ex;
        } catch (DataIntegrityViolationException ex) {
            log.error("Database constraint violation during category creation: {}", ex.getMessage());
            throw new ConflictException("Failed to create category due to data conflict.");
        } catch (Exception ex) {
            log.error("Unexpected error while creating category: {}", ex.getMessage());
            throw new GeneralServiceException("Failed to create category", ex);
        }
    }

    // --------------------------------------------------------
    // 2. Get Category By ID
    // --------------------------------------------------------
    @Override
    public CategoryDTO getCategoryById(Long id) {
        try {
            log.info("Fetching category by ID: {}", id);
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
            return mapToDTO(category);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error fetching category by ID {}: {}", id, ex.getMessage());
            throw new GeneralServiceException("Failed to fetch category", ex);
        }
    }

    // --------------------------------------------------------
    // 3. Get All Categories
    // --------------------------------------------------------
    @Override
    public List<CategoryDTO> getAllCategories() {
        try {
            log.info("Retrieving all categories...");
            List<Category> categories = categoryRepository.findAll();

            if (categories.isEmpty()) {
                throw new ResourceNotFoundException("No categories found");
            }

            List<CategoryDTO> response = categories.stream()
                    .map(this::mapToDTO)
                    .toList();

            log.info("Retrieved {} categories successfully", response.size());
            return response;

        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error retrieving all categories: {}", ex.getMessage());
            throw new GeneralServiceException("Failed to fetch categories", ex);
        }
    }

    // --------------------------------------------------------
    // 4. Update Category
    // --------------------------------------------------------
    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
        try {
            log.info("Updating category with ID: {}", id);

            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

            if (dto.getName() == null || dto.getName().trim().isEmpty()) {
                throw new BadRequestException("Category name cannot be empty.");
            }

            // Prevent duplicate category name conflicts
            if (categoryRepository.existsByName(dto.getName()) && !category.getName().equalsIgnoreCase(dto.getName())) {
                throw new ConflictException("Category already exists with name: " + dto.getName());
            }

            category.setName(dto.getName().trim());
            category.setDescription(dto.getDescription());

            Category updated = categoryRepository.save(category);
            log.info("Category updated successfully with ID: {}", updated.getCategoryId());
            return mapToDTO(updated);

        } catch (ResourceNotFoundException | BadRequestException | ConflictException ex) {
            throw ex;
        } catch (DataIntegrityViolationException ex) {
            log.error("Database constraint violation while updating category: {}", ex.getMessage());
            throw new ConflictException("Failed to update category due to data conflict.");
        } catch (Exception ex) {
            log.error("Unexpected error updating category: {}", ex.getMessage());
            throw new GeneralServiceException("Failed to update category", ex);
        }
    }

    // --------------------------------------------------------
    // 5. Delete Category
    // --------------------------------------------------------
    @Override
    public void deleteCategory(Long id) {
        try {
            log.info("Deleting category with ID: {}", id);
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

            categoryRepository.delete(category);
            log.info("Category deleted successfully with ID: {}", id);

        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error deleting category: {}", ex.getMessage());
            throw new GeneralServiceException("Failed to delete category", ex);
        }
    }

    // --------------------------------------------------------
    // Helper Method: Entity → DTO
    // --------------------------------------------------------
    private CategoryDTO mapToDTO(Category category) {
        return new CategoryDTO(
                category.getCategoryId(),
                category.getName(),
                category.getDescription()
        );
    }
}
