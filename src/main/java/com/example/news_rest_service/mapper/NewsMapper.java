package com.example.news_rest_service.mapper;

import com.example.news_rest_service.model.Comment;
import com.example.news_rest_service.model.News;
import com.example.news_rest_service.web.dto.request.UpdateNewsRequest;
import com.example.news_rest_service.web.dto.response.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapper.class, UserMapper.class, CategoryMapper.class})
public interface NewsMapper {

    @Mapping(source = "newsId", target = "id")
    News requestToNews(Long newsId, UpdateNewsRequest request);

    @Mapping(source = "news", target = "userId", qualifiedByName = "userToUserId")
    @Mapping(source = "news", target = "categoryId", qualifiedByName = "categoryToCategoryId")
    @Mapping(source = "news", target = "commentList", qualifiedByName = "commentToCommentList")
    NewsResponse newsToResponse(News news);
    @Named("userToUserId")
    default Long userToUserId(News news) {
        return news.getUser().getId();
    }
    @Named("categoryToCategoryId")
    default Long categoryToCategoryId(News news) {
        return news.getCategory().getId();
    }
    @Named("commentToCommentList")
    default List<Comment> commentToCommentList(News news) {
        return news.getCommentList();
    }

    @Mapping(source = "news", target = "id", qualifiedByName = "newsToNewsId")
    @Mapping(source = "news", target = "newsName", qualifiedByName = "newsToNewsName")
    @Mapping(source = "news", target = "commentSize", qualifiedByName = "commentListToCommentListSize")
    NewsWithCommentSizeResponse newsWithCommentSizeToResponse(News news);
    @Named("newsToNewsId")
    default Long newsToNewsId(News news) {
        return news.getId();
    }
    @Named("newsToNewsName")
    default String newsToNewsName(News news) {
        return news.getNewsName();
    }
    @Named("commentListToCommentListSize")
    default Long commentListToCommentListSize(News news) {
        return (long) news.getCommentList().size();
    }

    List<NewsWithCommentSizeResponse> newsListToResponseList(List<News> newsList);

    default NewsListResponse newsListToNewsListResponse(List<News> newsList) {
        return new NewsListResponse()
                .setNewsList(newsListToResponseList(newsList));
    }
}
