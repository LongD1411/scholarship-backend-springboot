package com.scholarship.dto.request;

import com.scholarship.entities.Country;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolRequest {
    private int id;
    private String logo;
    @NotBlank(message = "School name is required")
    private String name;
    private String provider;
    private String description;
    private int rankValue;
    @NotBlank(message = "Country code  is required")
    private String countryCode;
    private int students;
    private int fieldOfStudy;
    private String type;
    private String url;
    private String topReason;
}
