package com.example.news_rest_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<News> newsList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<Comment> commentList = new ArrayList<>();

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

    public void addComment(Comment comment) {
        if (commentList == null) {
            commentList = new ArrayList<>();
        }
        commentList.add(comment);
    }

    public void removeComment(Long commentId) {
        commentList = commentList.stream()
                .filter(comment -> !comment.getId().equals(commentId)).collect(Collectors.toList());
    }
}
