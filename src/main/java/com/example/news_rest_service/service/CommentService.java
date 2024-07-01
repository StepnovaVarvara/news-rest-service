package com.example.news_rest_service.service;


import com.example.news_rest_service.model.Comment;
import com.example.news_rest_service.model.News;
import com.example.news_rest_service.model.User;

import java.util.List;

public interface CommentService {
    List<Comment> findAllByNewsId(Long newsId);
    Comment findById(Long commentId);
    Comment save(Comment comment, News news, User user);
    Comment update(Comment comment);
    void deleteById(Long commentId);
}
