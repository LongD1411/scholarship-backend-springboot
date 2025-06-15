package com.scholarship.service.impl;

import com.scholarship.entities.AccountVerifyToken;
import com.scholarship.entities.User;
import com.scholarship.repositories.AccountVerifyTokenRepository;
import com.scholarship.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final AccountVerifyTokenRepository accountVerifyTokenRepository;

    public User validateToken(String token) {
        return accountVerifyTokenRepository.findByToken(token)
                .filter(this::isTokenValid)
                .map(AccountVerifyToken::getUser)
                .orElse(null);
    }
    private boolean isTokenValid(AccountVerifyToken tokenEntity) {
        return tokenEntity.getExpiryDate().toInstant()
                .isAfter(java.time.Instant.now());
    }
}
