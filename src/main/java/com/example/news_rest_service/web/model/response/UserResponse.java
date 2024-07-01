package com.example.news_rest_service.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserResponse {
    private Long id;
    private String userName;
    private List<NewsResponse> newsList;
    private List<CommentResponse> commentList;
}
