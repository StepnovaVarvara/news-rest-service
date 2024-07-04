package com.example.news_rest_service.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertUserRequest {
    @NotBlank(message = "Имя пользователя должно быть заполнено!")
    @Size(min = 3, max = 30, message = "Имя пользователя не может быть меньше {min} и больше {max}!")
    private String userName;
}
