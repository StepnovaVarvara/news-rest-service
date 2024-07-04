package com.example.news_rest_service.web.controller;

import com.example.news_rest_service.mapper.NewsMapper;
import com.example.news_rest_service.model.Category;
import com.example.news_rest_service.model.News;
import com.example.news_rest_service.model.User;
import com.example.news_rest_service.service.CategoryService;
import com.example.news_rest_service.service.NewsService;
import com.example.news_rest_service.service.UserService;
import com.example.news_rest_service.web.dto.filter.NewsFilter;
import com.example.news_rest_service.web.dto.request.UpdateNewsRequest;
import com.example.news_rest_service.web.dto.request.UpsertNewsRequest;
import com.example.news_rest_service.web.dto.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Slf4j
public class NewsController {

    private final NewsService newsService;
    private final UserService userService;
    private final CategoryService categoryService;

    private final NewsMapper newsMapper;

    @GetMapping("/filter")
    public NewsListResponse filterByCategory(NewsFilter filter) {
        return newsMapper.newsListToNewsListResponse(newsService.filterByCategory(filter));
    }

    @GetMapping
    public NewsListResponse findAll() {
        return newsMapper.newsListToNewsListResponse(newsService.findAll());
    }

    @GetMapping("/{id}")
    public NewsResponse findById(@PathVariable Long id) {
        return newsMapper.newsToResponse(newsService.findById(id));
    }

    @PostMapping
    @Transactional
    public NewsResponse create(@RequestBody @Valid UpsertNewsRequest request) {
        Category existedCategory = categoryService.findById(request.getCategoryId());
        User existedUser = userService.findById(request.getUserId());

        News news = News.builder()
                .newsName(request.getNewsName())
                .user(existedUser)
                .category(existedCategory)
                .build();

        return newsMapper.newsToResponse(newsService.save(news, existedCategory, existedUser));
    }

    @PutMapping("/{id}")
    public NewsResponse update(@PathVariable("id") Long newsId, @RequestParam Long userId, @RequestBody @Valid UpdateNewsRequest request) {
        News news = newsMapper.requestToNews(newsId, request)
                .setUser(userService.findById(userId))
                .setCategory(newsService.findById(newsId).getCategory());

        News updatedNews = newsService.update(news);

        return newsMapper.newsToResponse(updatedNews);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id, @RequestParam Long userId) {
        newsService.deleteById(id);
    }
}
