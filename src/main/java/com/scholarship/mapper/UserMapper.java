package com.scholarship.mapper;


import com.scholarship.dto.request.UserRequest;
import com.scholarship.dto.response.UserResponse;
import com.scholarship.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
    @Mapping(target = "password", ignore = true)
    User toUser(UserRequest request);

}
