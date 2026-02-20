package com.smartkhata.auth.controller;

import com.smartkhata.auth.dto.*;
import com.smartkhata.auth.service.impl.AuthService;
import com.smartkhata.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup/owner")
    public ApiResponse<AuthResponseDto> ownerSignup(
            @Valid @RequestBody OwnerSignupDto dto
    ) {
        return ApiResponse.<AuthResponseDto>builder()
                .success(true)
                .message("Owner registered successfully")
                .data(authService.ownerSignup(dto))
                .build();
    }

    @PostMapping("/signup/staff")
    public ApiResponse<AuthResponseDto> staffSignup(
            @Valid @RequestBody StaffSignupDto dto
    ) {
        return ApiResponse.<AuthResponseDto>builder()
                .success(true)
                .message("Staff registered successfully")
                .data(authService.staffSignup(dto))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponseDto> login(
            @Valid @RequestBody LoginDto dto
    ) {
        return ApiResponse.<AuthResponseDto>builder()
                .success(true)
                .message("Login successful")
                .data(authService.login(dto))
                .build();
    }
}
