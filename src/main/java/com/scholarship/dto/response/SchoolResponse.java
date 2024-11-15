package com.scholarship.dto.response;

import com.scholarship.entities.Country;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolResponse {
    private Integer id;

    private String logo;

    private String name;
    private String provider;

    private String description;

    private int rankValue;

    private String countryName;

    private String countryCode;
    private List<ScholarshipResponse> scholarships;
}
