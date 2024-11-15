package com.scholarship.dto.request;

import com.scholarship.dto.response.ScholarshipResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldOfStudyRequest {
    private int id;
    @NotBlank(message = "Field of study name is required")
    private String name;
}
