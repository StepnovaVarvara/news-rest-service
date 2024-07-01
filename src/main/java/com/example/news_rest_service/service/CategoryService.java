package com.example.news_rest_service.service;


import com.example.news_rest_service.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long categoryId);
    Category save(Category category);
    Category update(Category category);
    void deleteById(Long categoryId);
}
