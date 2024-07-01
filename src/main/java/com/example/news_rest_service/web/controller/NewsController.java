package com.example.news_rest_service.web.controller;

import com.example.news_rest_service.mapper.NewsMapper;
import com.example.news_rest_service.model.Category;
import com.example.news_rest_service.model.News;
import com.example.news_rest_service.model.User;
import com.example.news_rest_service.service.CategoryService;
import com.example.news_rest_service.service.NewsService;
import com.example.news_rest_service.service.UserService;
import com.example.news_rest_service.web.model.filter.NewsFilter;
import com.example.news_rest_service.web.model.request.UpdateNewsRequest;
import com.example.news_rest_service.web.model.request.UpsertNewsRequest;
import com.example.news_rest_service.web.model.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<NewsListResponse> filterByCategory(NewsFilter filter) {
        return ResponseEntity.ok(
                newsMapper.newsListToNewsListResponse(newsService.filterByCategory(filter))
        );
    }

    @GetMapping
    public ResponseEntity<NewsListResponse> findAll() {
        return ResponseEntity.ok(
                newsMapper.newsListToNewsListResponse(newsService.findAll())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(newsMapper.newsToResponse(newsService.findById(id)));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<NewsResponse> create(@RequestBody @Valid UpsertNewsRequest request) {
        Category existedCategory = categoryService.findById(request.getCategoryId());
        User existedUser = userService.findById(request.getUserId());

        News news = News.builder()
                .newsName(request.getNewsName())
                .user(existedUser)
                .category(existedCategory)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(
                newsMapper.newsToResponse(newsService.save(news, existedCategory, existedUser))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsResponse> update(@PathVariable("id") Long newsId, @RequestParam Long userId, @RequestBody @Valid UpdateNewsRequest request) {
        News news = newsMapper.requestToNews(newsId, request)
                .setUser(userService.findById(userId))
                .setCategory(newsService.findById(newsId).getCategory());

        News updatedNews = newsService.update(news);

        return ResponseEntity.ok(newsMapper.newsToResponse(updatedNews));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam Long userId) {
        newsService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
