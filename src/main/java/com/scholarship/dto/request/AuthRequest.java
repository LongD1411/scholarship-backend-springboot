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
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private  String password;
}
