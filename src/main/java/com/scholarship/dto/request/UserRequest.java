package com.scholarship.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scholarship.validator.DobConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("email")
    @NotBlank(message = "Email is required")
    private String email;


    private String password;


    @DobConstraint(min = 10)
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

}
