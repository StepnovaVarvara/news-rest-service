package com.example.news_rest_service.web.controller;

import com.example.news_rest_service.mapper.UserMapper;
import com.example.news_rest_service.model.User;
import com.example.news_rest_service.service.UserService;
import com.example.news_rest_service.web.dto.request.UpsertUserRequest;
import com.example.news_rest_service.web.dto.response.UserListResponse;
import com.example.news_rest_service.web.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @Transactional
    public UserListResponse findAll() {
        return userMapper.userListToUserResponseList(userService.findAll());
    }

    @GetMapping("/{id}")
    @Transactional
    public UserResponse findById(@PathVariable Long id) {
        return userMapper.userToResponse(userService.findById(id));
    }

    @PostMapping
    public UserResponse create(@RequestBody @Valid UpsertUserRequest request) {
        User newUser = userService.save(userMapper.requestToUser(request));

        return userMapper.userToResponse(newUser);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable("id") Long userId, @RequestBody @Valid UpsertUserRequest request) {
        User updatedUser = userService.update(userMapper.requestToUser(userId, request));

        return userMapper.userToResponse(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
