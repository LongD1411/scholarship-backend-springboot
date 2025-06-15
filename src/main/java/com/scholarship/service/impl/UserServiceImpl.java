package com.scholarship.service.impl;

import com.scholarship.dto.request.UserRequest;
import com.scholarship.dto.response.UserResponse;
import com.scholarship.entities.AccountVerifyToken;
import com.scholarship.entities.Role;
import com.scholarship.entities.User;
import com.scholarship.exception.AppException;
import com.scholarship.exception.ErrorCode;
import com.scholarship.mapper.UserMapper;
import com.scholarship.repositories.AccountVerifyTokenRepository;
import com.scholarship.repositories.RoleRepository;
import com.scholarship.repositories.UserRepository;
import com.scholarship.service.EmailService;
import com.scholarship.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountVerifyTokenRepository accountVerifyTokenRepository;
    private final EmailService emailService;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    String backEndURL = "http://localhost:8088/auth/verify-email?token=";
    @Override
    public UserResponse createUser(UserRequest request) {
        String email = request.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        User user = userMapper.toUser(request);
        user.setActive(true);
        user.setVerified(false);
        Role role = roleRepository.findByName("USER");
        user.setRole(role);
        String password = request.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        String verifyToken = UUID.randomUUID().toString();
        Date issueTime = new Date();
        AccountVerifyToken accountVerifyToken = AccountVerifyToken.builder()
                .token(verifyToken)
                .expiryDate(new Date(Instant.ofEpochMilli(issueTime.getTime())
                        .plus(1, ChronoUnit.DAYS)
                        .toEpochMilli()))
                .user(user)
                .build();
        userRepository.save(user);
        accountVerifyTokenRepository.save(accountVerifyToken);
        emailService.sendVerificationEmail(email,backEndURL + verifyToken);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse verifyUser(String token) {
        Optional<AccountVerifyToken> optionalToken = accountVerifyTokenRepository.findByToken(token);
        if (optionalToken.isEmpty()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        AccountVerifyToken verificationToken = optionalToken.get();

        // Kiểm tra token có hết hạn không
        if (verificationToken.getExpiryDate().before(new Date())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Kích hoạt tài khoản người dùng
        User user = verificationToken.getUser();
        user.setVerified(true);
        userRepository.save(user);

        // Xóa token sau khi xác nhận thành công
        accountVerifyTokenRepository.delete(verificationToken);
        return userMapper.toUserResponse(user);
    }
}
