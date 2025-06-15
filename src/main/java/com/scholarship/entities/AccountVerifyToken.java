package com.scholarship.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@Data
@Getter
@Setter
@Table(name = "account_verify_token")
@AllArgsConstructor
@NoArgsConstructor
public class AccountVerifyToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token; // UUID token

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private Date expiryDate;
}
