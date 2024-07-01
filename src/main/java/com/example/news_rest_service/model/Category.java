package com.example.news_rest_service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@Entity(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @Builder.Default
    @ToString.Exclude
    private List<News> newsList = new ArrayList<>();

    public void addNews(News news) {
        if (newsList == null) {
            newsList = new ArrayList<>();
        }
        newsList.add(news);
    }
    public void removeNews(Long newsId) {
        newsList = newsList.stream()
                .filter(news -> !news.getId().equals(newsId)).collect(Collectors.toList());
    }
}
