package com.smartkhata.auth.controller;

import com.smartkhata.auth.dto.UserDto;
import com.smartkhata.auth.service.impl.UserService;
import com.smartkhata.common.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserDto> create(@Valid @RequestBody UserDto dto) {
        return ApiResponse.<UserDto>builder()
                .success(true)
                .message("User created successfully")
                .data(userService.create(dto))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserDto> get(@PathVariable Long id) {
        return ApiResponse.<UserDto>builder()
                .success(true)
                .message("User fetched successfully")
                .data(userService.getById(id))
                .build();
    }

    @GetMapping("/vendor/{vendorId}")
    public ApiResponse<List<UserDto>> getByVendor(
            @PathVariable Long vendorId
    ) {
        return ApiResponse.<List<UserDto>>builder()
                .success(true)
                .message("Users fetched successfully")
                .data(userService.getByVendor(vendorId))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserDto> update(
            @PathVariable Long id,
            @RequestBody UserDto dto
    ) {
        return ApiResponse.<UserDto>builder()
                .success(true)
                .message("User updated successfully")
                .data(userService.update(id, dto))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("User deleted successfully")
                .build();
    }
}
