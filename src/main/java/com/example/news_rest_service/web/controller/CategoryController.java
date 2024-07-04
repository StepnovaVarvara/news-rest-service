package com.example.news_rest_service.web.controller;

import com.example.news_rest_service.mapper.CategoryMapper;
import com.example.news_rest_service.model.Category;
import com.example.news_rest_service.service.CategoryService;
import com.example.news_rest_service.web.dto.request.UpsertCategoryRequest;
import com.example.news_rest_service.web.dto.response.CategoryListResponse;
import com.example.news_rest_service.web.dto.response.CategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    @Transactional
    public CategoryListResponse findAll() {
        return categoryMapper.categoryListToCategoryResponseList(categoryService.findAll());
    }

    @GetMapping("/{id}")
    @Transactional
    public CategoryResponse findById(@PathVariable Long id) {
        return categoryMapper.categoryToResponse(categoryService.findById(id));
    }

    @PostMapping
    public CategoryResponse create(@Valid @RequestBody UpsertCategoryRequest request) {
        Category newCategory = categoryService.save(categoryMapper.requestToCategory(request));

        return categoryMapper.categoryToResponse(newCategory);
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable("id") Long categoryId, @Valid @RequestBody UpsertCategoryRequest request) {
        Category updatedCategory = categoryService.update(categoryMapper.requestToCategory(categoryId, request));

        return categoryMapper.categoryToResponse(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}
