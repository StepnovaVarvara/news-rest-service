package com.example.news_rest_service.web.model.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModelEntityFilter {
    private Integer pageSize;
    private Integer pageNumber;
}
