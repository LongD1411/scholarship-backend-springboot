package com.scholarship.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    private String password;
    private String email;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "is_Active")
    private  boolean active;
    @Column(name= "is_verified")
    private boolean isVerified;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserAuthProvider> authProviders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserScholarship> savedScholarships;

}
