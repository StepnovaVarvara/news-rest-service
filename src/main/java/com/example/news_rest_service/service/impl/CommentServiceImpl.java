package com.example.news_rest_service.service.impl;

import com.example.news_rest_service.aop.CommentChecker;
import com.example.news_rest_service.exception.EntityNotFoundException;
import com.example.news_rest_service.model.Comment;
import com.example.news_rest_service.model.News;
import com.example.news_rest_service.model.User;
import com.example.news_rest_service.repository.CommentRepository;
import com.example.news_rest_service.service.CommentService;
import com.example.news_rest_service.service.NewsService;
import com.example.news_rest_service.service.UserService;
import com.example.news_rest_service.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final NewsService newsService;
    private final UserService userService;

    @Override
    public List<Comment> findAllByNewsId(Long newsId) {
        return commentRepository.findAllByNewsId(newsId);
    }


    @Override
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Комментарий с ID {0} не найден", commentId)
                ));
    }

    @Override
    @Transactional
    public Comment save(Comment comment, News news, User user) {
        Comment savedComment = commentRepository.save(comment);

        News existedNews = newsService.findById(news.getId());
        existedNews.addComment(savedComment);

        User existedUser = userService.findById(user.getId());
        existedUser.addComment(savedComment);

        return savedComment;
    }

    @Override
    @Transactional
    @CommentChecker
    public Comment update(Comment comment) {
        Comment existedComment = findById(comment.getId());

        BeanUtils.copyNonNullProperties(comment, existedComment);

        return commentRepository.save(comment);
    }

    @Override
    @CommentChecker
    public void deleteById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

}
