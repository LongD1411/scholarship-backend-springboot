package com.scholarship.dto.response;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScholarshipResponse {
    private Integer id;
    private String name;
    private String description;
    private String eligibility;
    private String grantAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String gpa;
    private String url;
    private Boolean isActive = true;
    private int quantity;
    private String schoolName;
    private String countryName;
    private String fieldOfStudyName;
    private String schoolId;
    private String schoolRank;
    private String schoolLogo;
    private String fieldOfStudyId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
    