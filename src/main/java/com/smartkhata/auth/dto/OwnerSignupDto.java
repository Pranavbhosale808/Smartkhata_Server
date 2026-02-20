package com.smartkhata.auth.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerSignupDto {

    @NotBlank private String shopName;
    @NotBlank private String ownerName;

    @NotBlank private String phone;

    @NotBlank private String name;

    @Email @NotBlank
    private String email;

    @NotBlank
    private String password;
}
