package com.scholarship.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_auth_providers")
public class UserAuthProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String provider; // GOOGLE, FACEBOOK, LOCAL

    @Column(nullable = false)
    private String providerId; // ID của tài khoản từ Google/Facebook
}
