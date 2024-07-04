package com.example.news_rest_service.service;

import com.example.news_rest_service.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long userId);
    User save(User user);
    User update(User user);
    void deleteById(Long userId);
}
