package com.example.news_rest_service.repository;

import com.example.news_rest_service.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByNewsId(Long newsId);
}
