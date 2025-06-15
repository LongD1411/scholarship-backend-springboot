package com.scholarship.controller;

import com.nimbusds.jose.JOSEException;
import com.scholarship.dto.request.AuthRequest;
import com.scholarship.dto.request.TokenRequest;
import com.scholarship.dto.request.UserRequest;
import com.scholarship.dto.response.ApiResponse;
import com.scholarship.dto.response.AuthResponse;
import com.scholarship.dto.response.UserResponse;
import com.scholarship.entities.User;
import com.scholarship.repositories.UserRepository;
import com.scholarship.service.AuthService;
import com.scholarship.service.UserService;
import com.scholarship.service.VerificationTokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {
    private  final AuthService authService;
    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final UserRepository userRepository;
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
    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody UserRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }
    @GetMapping("/verify-email")
    public RedirectView verifyEmail(@RequestParam("token") String token) {
        User user = verificationTokenService.validateToken(token);

        if (user == null) {
            return new RedirectView("http://localhost:4200/dang-nhap?verify=fail");
        }

        user.setVerified(true);
        userRepository.save(user);

        return new RedirectView("http://localhost:4200/dang-nhap?verify=success");
    }

}
