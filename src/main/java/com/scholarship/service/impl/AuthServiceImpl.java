package com.scholarship.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.scholarship.dto.request.AuthRequest;
import com.scholarship.dto.request.TokenRequest;
import com.scholarship.dto.response.AuthResponse;
import com.scholarship.dto.response.TokenValidResponse;
import com.scholarship.entities.InvalidatedToken;
import com.scholarship.entities.User;
import com.scholarship.exception.AppException;
import com.scholarship.exception.ErrorCode;
import com.scholarship.repositories.InvalidatedTokenRepository;
import com.scholarship.repositories.UserRepository;
import com.scholarship.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final InvalidatedTokenRepository invalidatedTokenRepository;
    @Value("${jwt.secretKey}")
    private String secretKey;
    @Override
    public AuthResponse authenticate(AuthRequest request) {
        Optional<User> optional = userRepository.findByUserName(request.getUserName());
        if(optional.isEmpty()) throw  new AppException(ErrorCode.USER_NOT_EXISTED);
        User user = optional.get();
        boolean matchPassword = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!matchPassword) throw new AppException(ErrorCode.UNAUTHENTICATED);
        var token = generateToken(user);
        AuthResponse authResponse = AuthResponse.builder().token(token.token).expiryTime(token.expiredTime).build();
        return authResponse;
    }

    private  Token generateToken(User user){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        Date issueTime = new Date();
        Date expiredTime = new Date(Instant.ofEpochMilli(issueTime.getTime())
                .plus(1, ChronoUnit.DAYS)
                .toEpochMilli()
        );
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())
                .subject(user.getUserName())
                .expirationTime(expiredTime)
                .issueTime(issueTime)
                .claim("scope",buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader,payload);
        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return new Token(jwsObject.serialize(), expiredTime);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        user.getRoles().forEach(role -> stringJoiner.add("ROLE_" +role.getName()));
        return stringJoiner.toString();
    }
    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(secretKey.getBytes());
        Date expiryTime = (isRefresh) ?
                new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                        .toInstant()
                        .plus(1,ChronoUnit.DAYS)
                        .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verified = signedJWT.verify(verifier);
        if (!(verified || expiryTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        return signedJWT;
    }
    @Override
    public void logout(TokenRequest request) throws ParseException, JOSEException {
        var signToken = verifyToken(request.getToken(),false);

        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();
        invalidatedTokenRepository.save(invalidatedToken);
    }
//    @Override
//    public AuthResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
//        var signedJWT = verifyToken(request.getToken(),true);
//
//        var jit = signedJWT.getJWTClaimsSet().getJWTID();
//        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
//
//        InvalidatedToken invalidatedToken =
//                InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();
//
//        invalidatedTokenRepository.save(invalidatedToken);
//
//        var username = signedJWT.getJWTClaimsSet().getSubject();
//
//        var user = userRepository.findByPhoneNumber(username).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
//
//        var token = generateToken(user);
//
//        return AuthResponse.builder()
//                .token(token.token)
//                .expiryTime(token.expiredTime)
//                .build();
//    }
@Override
public TokenValidResponse introspect(TokenRequest request) throws ParseException, JOSEException {
    var token = request.getToken();
    boolean isValid = true;
    try {
        verifyToken(token,false);
    }catch (AppException e){
        isValid = false;
    }
    return TokenValidResponse.builder()
            .valid(isValid)
            .build();
}
    private record Token(String token, Date expiredTime){};
}
