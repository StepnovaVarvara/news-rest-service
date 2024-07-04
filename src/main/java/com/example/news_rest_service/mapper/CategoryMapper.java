package com.example.news_rest_service.mapper;

import com.example.news_rest_service.model.Category;
import com.example.news_rest_service.web.dto.request.UpsertCategoryRequest;
import com.example.news_rest_service.web.dto.response.CategoryListResponse;
import com.example.news_rest_service.web.dto.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {NewsMapper.class})
public interface CategoryMapper {
    Category requestToCategory(UpsertCategoryRequest request);

    @Mapping(source = "categoryId", target = "id")
    Category requestToCategory(Long categoryId, UpsertCategoryRequest request);

    CategoryResponse categoryToResponse(Category category);

    default CategoryListResponse categoryListToCategoryResponseList(List<Category> categoryList) {
        CategoryListResponse response = new CategoryListResponse();
        response.setCategoryList(categoryList.stream()
                .map(this::categoryToResponse).collect(Collectors.toList()));

        return response;
    }
}
