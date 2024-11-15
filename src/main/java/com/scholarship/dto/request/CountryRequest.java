package com.scholarship.dto.request;

import com.scholarship.entities.School;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryRequest {
    @NotBlank(message = "Country code is required")
    private String code;
    @NotBlank(message = "Country name is required")
    private String name;

    private String continent;
}
