package com.scholarship.repositories;

import com.scholarship.entities.Scholarship;
import com.scholarship.entities.User;
import com.scholarship.entities.UserScholarship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserScholarshipRepository extends JpaRepository<UserScholarship, Long> {
    @Query("SELECT us.scholarship FROM UserScholarship us WHERE us.user.email = :email")
    List<Scholarship> findScholarshipsByUserEmail(@Param("email") String email);
    boolean existsByUserAndScholarship(User user, Scholarship scholarship);
    Optional<UserScholarship> findByUserAndScholarship(User user, Scholarship scholarship);
}