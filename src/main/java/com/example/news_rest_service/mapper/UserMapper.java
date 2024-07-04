package com.example.news_rest_service.mapper;

import com.example.news_rest_service.model.User;
import com.example.news_rest_service.web.dto.request.UpsertUserRequest;
import com.example.news_rest_service.web.dto.response.UserListResponse;
import com.example.news_rest_service.web.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {NewsMapper.class, CommentMapper.class})
public interface UserMapper {
    User requestToUser(UpsertUserRequest request);

    @Mapping(source = "userId", target = "id")
    User requestToUser(Long userId, UpsertUserRequest request);

    UserResponse userToResponse(User user);

    default UserListResponse userListToUserResponseList(List<User> userList) {
        UserListResponse response = new UserListResponse();
        response.setUserList(userList.stream()
                .map(this::userToResponse).collect(Collectors.toList()));

        return response;
    }
}
