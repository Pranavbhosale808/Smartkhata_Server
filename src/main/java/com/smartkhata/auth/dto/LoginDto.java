package com.smartkhata.auth.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {

    @Email @NotBlank
    private String email;

    @NotBlank
    private String password;
}
