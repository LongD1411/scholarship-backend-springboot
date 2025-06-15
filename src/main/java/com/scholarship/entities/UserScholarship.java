package com.scholarship.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_scholarships")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserScholarship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Scholarship scholarship;

    private LocalDateTime savedAt;

    @PrePersist
    public void onSave() {
        savedAt = LocalDateTime.now();
    }
}