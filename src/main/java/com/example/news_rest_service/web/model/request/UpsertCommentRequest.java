package com.example.news_rest_service.web.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpsertCommentRequest {
    @NotBlank(message = "Текст комментария должен быть заполнен!")
    private String text;
    @NotNull(message = "ID новости должно быть указано!")
    @Positive(message = "ID новости должно быть больше 0!")
    private Long newsId;
    @NotNull(message = "ID пользователя должно быть указано!")
    @Positive(message = "ID пользователя должно быть больше 0!")
    private Long userId;
}
