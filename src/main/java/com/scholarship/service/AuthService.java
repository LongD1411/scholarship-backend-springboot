package com.scholarship.service;

import com.nimbusds.jose.JOSEException;
import com.scholarship.dto.request.AuthRequest;
import com.scholarship.dto.request.TokenRequest;
import com.scholarship.dto.response.AuthResponse;
import com.scholarship.dto.response.TokenValidResponse;

import java.text.ParseException;

public interface AuthService {
    AuthResponse authenticate(AuthRequest request);
    void logout(TokenRequest request) throws ParseException, JOSEException;
    TokenValidResponse introspect(TokenRequest request) throws ParseException, JOSEException;
}
