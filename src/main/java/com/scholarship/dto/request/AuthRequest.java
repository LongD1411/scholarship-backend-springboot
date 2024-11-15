package com.scholarship.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    @NotBlank(message = "User name is required")
    private String userName;
    @NotBlank(message = "User name is required")
    private  String password;
}
