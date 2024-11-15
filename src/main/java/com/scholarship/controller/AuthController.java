package com.scholarship.controller;

import com.nimbusds.jose.JOSEException;
import com.scholarship.dto.request.AuthRequest;
import com.scholarship.dto.request.TokenRequest;
import com.scholarship.dto.response.ApiResponse;
import com.scholarship.dto.response.AuthResponse;
import com.scholarship.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {
    private  final AuthService authService;
    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody TokenRequest request) throws ParseException, JOSEException {
        authService.logout(request);
        return ApiResponse.<Void>builder().build();
    }
    @PostMapping("/login")
    public ApiResponse<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {
        var result = authService.authenticate(request);
        return ApiResponse.<AuthResponse>builder().result(result).build();
    }
}
