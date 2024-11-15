package com.scholarship.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scholarship.entities.FieldOfStudy;
import com.scholarship.entities.School;
import com.scholarship.enums.DegreeLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScholarshipRequest {
    private int id;
    @NotBlank(message = "Scholarship name  is required")
    private String name;
    private String description;
    private String eligibility;
    private String grantAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive = true;
    private int quantity;
    private String url;
    private String gpa;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @NotNull(message = "School id is required")
    private Integer schoolId;
    @NotNull(message = "Field of study id is required")
    private Integer fieldOfStudyId;
}
