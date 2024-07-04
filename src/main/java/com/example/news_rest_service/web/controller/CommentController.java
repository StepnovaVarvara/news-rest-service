package com.example.news_rest_service.web.controller;

import com.example.news_rest_service.mapper.CommentMapper;
import com.example.news_rest_service.model.Comment;
import com.example.news_rest_service.model.News;
import com.example.news_rest_service.model.User;
import com.example.news_rest_service.service.CommentService;
import com.example.news_rest_service.service.NewsService;
import com.example.news_rest_service.service.UserService;
import com.example.news_rest_service.web.dto.request.UpdateCommentRequest;
import com.example.news_rest_service.web.dto.request.UpsertCommentRequest;
import com.example.news_rest_service.web.dto.response.CommentResponse;
import com.example.news_rest_service.web.dto.response.CommentToNewsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final NewsService newsService;
    private final UserService userService;

    private final CommentMapper commentMapper;


    @GetMapping("/findAll/{newsId}")
    @Transactional
    public CommentToNewsResponse findAllByNewsId(@PathVariable Long newsId) {
        return commentMapper.commentListToCommentListResponse(commentService.findAllByNewsId(newsId));
    }

    @GetMapping("/{id}")
    @Transactional
    public CommentResponse findById(@PathVariable Long id) {
        return commentMapper.commentToResponse(commentService.findById(id));
    }

    @PostMapping
    public CommentResponse create(@RequestBody @Valid UpsertCommentRequest request) {
        News existedNews = newsService.findById(request.getNewsId());
        User existedUser = userService.findById(request.getUserId());

        Comment comment = Comment.builder().text(request.getText())
                .user(existedUser)
                .news(existedNews)
                .build();

        return commentMapper.commentToResponse(commentService.save(comment, existedNews, existedUser));
    }

    @PutMapping("/{id}")
    public CommentResponse update(@PathVariable("id") Long commentId, @RequestParam Long userId, @RequestBody @Valid UpdateCommentRequest request) {
        Comment comment = commentMapper.requestToComment(commentId, request)
                .setUser(userService.findById(userId))
                .setNews(commentService.findById(commentId).getNews());

        Comment updatedComment = commentService.update(comment);

        return commentMapper.commentToResponse(updatedComment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestParam Long userId) {
        commentService.deleteById(id);
    }
}
