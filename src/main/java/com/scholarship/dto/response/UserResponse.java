package com.scholarship.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    @JsonProperty("user_id")
    private Long userID;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;
    @JsonProperty("email")
    private String email;
    @JsonProperty("is_active")
    private boolean active;
}
