package com.example.news_rest_service.mapper;

import com.example.news_rest_service.model.News;
import com.example.news_rest_service.service.CommentService;
import com.example.news_rest_service.web.model.response.NewsResponse;
import com.example.news_rest_service.web.model.response.NewsWithCommentSizeResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public abstract class NewsMapperDelegate implements NewsMapper {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public NewsResponse newsToResponse(News news) {
        return new NewsResponse()
                .setId(news.getId())
                .setNewsName(news.getNewsName())
                .setCommentList(commentMapper.commentListToResponseList(news.getCommentList()))
                .setUserId(news.getUser().getId())
                .setCategoryId(news.getCategory().getId());
    }

    @Override
    public NewsWithCommentSizeResponse newsWithCommentSizeToResponse(News news) {
        return new NewsWithCommentSizeResponse()
                .setId(news.getId())
                .setNewsName(news.getNewsName())
                .setCommentSize((long) commentService.findAllByNewsId(news.getId()).size());
    }

    @Override
    public List<NewsWithCommentSizeResponse> newsListToResponseList(List<News> newsList) {
        return newsList.stream()
                .map(this::newsWithCommentSizeToResponse)
                .collect(Collectors.toList());
    }
}
