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
    private String topReason;
    private int rankValue;

    private String countryName;
    private int students;
    private int fieldOfStudy;
    private String type;
    private String url;
    private String countryCode;
    private List<ScholarshipResponse> scholarships;
}
