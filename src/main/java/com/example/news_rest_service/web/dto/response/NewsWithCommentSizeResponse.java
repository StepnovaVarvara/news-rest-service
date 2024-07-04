package com.example.news_rest_service.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class NewsWithCommentSizeResponse {
    private Long id;
    private String newsName;
    private Long commentSize;
}
