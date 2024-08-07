package com.example.news_rest_service.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentRequest {
    @NotBlank(message = "Текст комментария должен быть заполнен!")
    private String text;
}
