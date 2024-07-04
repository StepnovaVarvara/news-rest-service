package com.example.news_rest_service.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertNewsRequest {
    @NotBlank(message = "Название новости должно быть заполнено!")
    private String newsName;

    @NotNull(message = "ID категории должно быть указано!")
    @Positive(message = "ID категории должно быть больше 0!")
    private Long categoryId;

    @NotNull(message = "ID пользователя должно быть указано!")
    @Positive(message = "ID пользователя должно быть больше 0!")
    private Long userId;
}