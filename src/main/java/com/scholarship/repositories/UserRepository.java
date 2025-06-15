package com.scholarship.repositories;

import com.scholarship.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
