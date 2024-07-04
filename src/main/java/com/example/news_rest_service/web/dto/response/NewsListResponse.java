package com.example.news_rest_service.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class NewsListResponse {
    private List<NewsWithCommentSizeResponse> newsList = new ArrayList<>();
}
