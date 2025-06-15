package com.scholarship.service;

import com.scholarship.dto.request.UserRequest;
import com.scholarship.dto.response.UserResponse;
import org.springframework.stereotype.Service;


public interface UserService {
    UserResponse createUser(UserRequest request);
    UserResponse verifyUser(String token);
}
