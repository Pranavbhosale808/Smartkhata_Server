package com.smartkhata.auth.dto;

import com.smartkhata.auth.entity.Role;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotNull(message = "Role is required")
    private Role role;

    @NotNull(message = "Vendor ID is required")
    private Long vendorId;
}
