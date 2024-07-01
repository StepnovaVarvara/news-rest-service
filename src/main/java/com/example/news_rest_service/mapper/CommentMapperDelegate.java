package com.example.news_rest_service.mapper;

import com.example.news_rest_service.model.Comment;
import com.example.news_rest_service.web.model.response.CommentResponse;

import java.util.List;
import java.util.stream.Collectors;

public abstract class CommentMapperDelegate implements CommentMapper {

    @Override
    public CommentResponse commentToResponse(Comment comment) {
        return new CommentResponse()
                .setId(comment.getId())
                .setText(comment.getText())
                .setUserId(comment.getUser().getId())
                .setNewsId(comment.getNews().getId());
    }

    @Override
    public List<CommentResponse> commentListToResponseList(List<Comment> commentList) {
        return commentList.stream()
                .map(this::commentToResponse)
                .collect(Collectors.toList());
    }
}
