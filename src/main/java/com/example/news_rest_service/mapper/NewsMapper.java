package com.example.news_rest_service.mapper;

import com.example.news_rest_service.model.News;
import com.example.news_rest_service.web.model.request.UpdateNewsRequest;
import com.example.news_rest_service.web.model.response.NewsListResponse;
import com.example.news_rest_service.web.model.response.NewsResponse;
import com.example.news_rest_service.web.model.response.NewsUpdateResponse;
import com.example.news_rest_service.web.model.response.NewsWithCommentSizeResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapper.class, UserMapper.class, CategoryMapper.class})
public interface NewsMapper {

    @Mapping(source = "newsId", target = "id")
    News requestToNews(Long newsId, UpdateNewsRequest request);

    NewsResponse newsToResponse(News news);

    NewsUpdateResponse newsUpdateToResponse(News news);

    NewsWithCommentSizeResponse newsWithCommentSizeToResponse(News news);

    List<NewsWithCommentSizeResponse> newsListToResponseList(List<News> newsList);

    default NewsListResponse newsListToNewsListResponse(List<News> newsList) {
        return new NewsListResponse()
                .setNewsList(newsListToResponseList(newsList));
    }
}
