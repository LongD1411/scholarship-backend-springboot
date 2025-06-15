package com.scholarship.repositories;

import com.scholarship.entities.AccountVerifyToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountVerifyTokenRepository extends JpaRepository<AccountVerifyToken,String> {
    Optional<AccountVerifyToken> findByToken(String token);
}
