package com.example.news_rest_service.mapper;

import com.example.news_rest_service.model.Comment;
import com.example.news_rest_service.web.model.request.UpdateCommentRequest;
import com.example.news_rest_service.web.model.request.UpsertCommentRequest;
import com.example.news_rest_service.web.model.response.*;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(CommentMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    Comment requestToComment(UpsertCommentRequest request);

    @Mapping(source = "commentId", target = "id")
    Comment requestToComment(Long commentId, UpdateCommentRequest request);

    CommentResponse commentToResponse(Comment comment);
    CommentUpdateResponse commentUpdateToResponse(Comment comment);

    List<CommentResponse> commentListToResponseList(List<Comment> commentList);

    default CommentToNewsResponse commentListToCommentListResponse(List<Comment> commentList) {
        return new CommentToNewsResponse()
                .setCommentList(commentListToResponseList(commentList));
    }
}
