package com.example.news_rest_service.service.impl;


import com.example.news_rest_service.aop.NewsChecker;
import com.example.news_rest_service.exception.EntityNotFoundException;
import com.example.news_rest_service.model.Category;
import com.example.news_rest_service.model.News;
import com.example.news_rest_service.model.User;
import com.example.news_rest_service.repository.NewsRepository;
import com.example.news_rest_service.repository.NewsSpecification;
import com.example.news_rest_service.service.CategoryService;
import com.example.news_rest_service.service.NewsService;
import com.example.news_rest_service.service.UserService;
import com.example.news_rest_service.utils.BeanUtils;
import com.example.news_rest_service.web.dto.filter.NewsFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final CategoryService categoryService;
    private final UserService userService;

    @Override
    public List<News> filterByCategory(NewsFilter filter) {
        return newsRepository.findAll(NewsSpecification.withFilter(filter));
    }

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    @Transactional
    public News findById(Long newsId) {
        return newsRepository.findById(newsId)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Новость с ID {0} не найдена", newsId)
                ));
    }


    @Override
    @Transactional
    public News save(News news, Category category, User user) {
        News savedNews = newsRepository.save(news);

        Category existedCategory = categoryService.findById(category.getId());
        existedCategory.addNews(savedNews);

        User existedUser = userService.findById(user.getId());
        existedUser.addNews(savedNews);

        return savedNews;
    }

    @Override
    @Transactional
    @NewsChecker
    public News update(News news) {
        News existedNews = findById(news.getId());

        BeanUtils.copyNonNullProperties(news, existedNews);

        return newsRepository.save(news);
    }

    @Override
    @NewsChecker
    public void deleteById(Long newsId) {
        newsRepository.deleteById(newsId);
    }
}
