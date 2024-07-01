package com.example.news_rest_service.web.controller;

import com.example.news_rest_service.mapper.CommentMapper;
import com.example.news_rest_service.model.Comment;
import com.example.news_rest_service.model.News;
import com.example.news_rest_service.model.User;
import com.example.news_rest_service.service.CommentService;
import com.example.news_rest_service.service.NewsService;
import com.example.news_rest_service.service.UserService;
import com.example.news_rest_service.web.model.request.UpdateCommentRequest;
import com.example.news_rest_service.web.model.request.UpsertCommentRequest;
import com.example.news_rest_service.web.model.response.CommentResponse;
import com.example.news_rest_service.web.model.response.CommentToNewsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommentToNewsResponse> findAllByNewsId(@PathVariable Long newsId) {
        return ResponseEntity.ok(
                commentMapper.commentListToCommentListResponse(commentService.findAllByNewsId(newsId))
        );
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(commentMapper.commentToResponse(commentService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid UpsertCommentRequest request) {
        News existedNews = newsService.findById(request.getNewsId());
        User existedUser = userService.findById(request.getUserId());

        Comment comment = Comment.builder().text(request.getText())
                .user(existedUser)
                .news(existedNews)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(
                commentMapper.commentToResponse(commentService.save(comment, existedNews, existedUser))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@PathVariable("id") Long commentId, @RequestParam Long userId, @RequestBody @Valid UpdateCommentRequest request) {
        Comment comment = commentMapper.requestToComment(commentId, request)
                .setUser(userService.findById(userId))
                .setNews(commentService.findById(commentId).getNews());

        Comment updatedComment = commentService.update(comment);

        return ResponseEntity.ok(commentMapper.commentToResponse(updatedComment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam Long userId) {
        commentService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
