package com.scholarship.service;

import com.scholarship.entities.User;

public interface VerificationTokenService {
     User validateToken(String token);
}
