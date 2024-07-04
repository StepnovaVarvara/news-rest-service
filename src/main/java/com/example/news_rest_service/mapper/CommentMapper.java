package com.example.news_rest_service.mapper;

import com.example.news_rest_service.model.Comment;
import com.example.news_rest_service.web.dto.request.UpdateCommentRequest;
import com.example.news_rest_service.web.dto.response.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class, NewsMapper.class})
public interface CommentMapper {

    @Mapping(source = "commentId", target = "id")
    Comment requestToComment(Long commentId, UpdateCommentRequest request);

    @Mapping(source = "comment", target = "userId", qualifiedByName = "userToUserId")
    @Mapping(source = "comment", target = "newsId", qualifiedByName = "newsToNewsId")
    CommentResponse commentToResponse(Comment comment);
    @Named("userToUserId")
    default Long userToUserId(Comment comment) {
        return comment.getUser().getId();
    }
    @Named("newsToNewsId")
    default Long newsToNewsId(Comment comment) {
        return comment.getNews().getId();
    }

    List<CommentResponse> commentListToResponseList(List<Comment> commentList);

    default CommentToNewsResponse commentListToCommentListResponse(List<Comment> commentList) {
        return new CommentToNewsResponse()
                .setCommentList(commentListToResponseList(commentList));
    }
}
