package com.example.news_rest_service.service;

import com.example.news_rest_service.model.Category;
import com.example.news_rest_service.model.News;
import com.example.news_rest_service.model.User;
import com.example.news_rest_service.web.dto.filter.NewsFilter;

import java.util.List;

public interface NewsService {
    List<News> filterByCategory(NewsFilter filter);
    List<News> findAll();
    News findById(Long newsId);
    News save(News news, Category category, User user);
    News update(News news);
    void deleteById(Long newsId);
}
