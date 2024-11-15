package com.scholarship.dto.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponse {
    private String code;

    private String name;

    private String continent;

    private List<SchoolResponse> schools;
}
