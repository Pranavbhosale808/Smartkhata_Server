package com.smartkhata.auth.dto;

import com.smartkhata.auth.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto {

    private Long userId;
    private Long vendorId;
    private String name;
    private String email;
    private Role role;
    private String token;
}
