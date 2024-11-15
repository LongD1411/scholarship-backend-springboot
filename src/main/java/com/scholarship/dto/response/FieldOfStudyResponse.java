package com.scholarship.dto.response;

import com.scholarship.entities.Scholarship;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldOfStudyResponse {
    private Integer id;
    private String name;
    private List<ScholarshipResponse> scholarships;
}
