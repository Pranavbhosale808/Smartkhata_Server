package com.smartkhata.auth.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffSignupDto {

    @NotNull
    private Long vendorId;

    @NotBlank
    private String name;

    @Email @NotBlank
    private String email;

    @NotBlank
    private String password;
}
