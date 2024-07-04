package com.example.news_rest_service.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertCategoryRequest {
    @NotBlank(message = "Название категории должно быть заполнено!")
    private String categoryName;
}
