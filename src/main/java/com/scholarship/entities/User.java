package com.scholarship.entities;

import jakarta.persistence.*;
import lombok.*;

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

    private String userName;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

}
