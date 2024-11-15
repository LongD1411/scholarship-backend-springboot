package com.scholarship.entities;

import com.scholarship.enums.DegreeLevel;
import com.scholarship.enums.ScholarshipType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Scholarship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String eligibility;

    @Column(columnDefinition = "TEXT")
    private String url;
    private String grantAmount;

    private String gpa;
    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isActive = true;


    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    private School school;

    @ManyToOne(fetch = FetchType.EAGER)
    private FieldOfStudy fieldOfStudy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
