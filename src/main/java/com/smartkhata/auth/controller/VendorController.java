package com.smartkhata.auth.controller;

import com.smartkhata.auth.dto.VendorDto;
import com.smartkhata.auth.dto.VendorResponseDto;
import com.smartkhata.auth.service.impl.VendorService;
import com.smartkhata.common.response.ApiResponse;
import com.smartkhata.common.security.VendorContext;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @PostMapping
    public ApiResponse<VendorDto> create(@RequestBody VendorDto dto) {
        return ApiResponse.<VendorDto>builder()
                .success(true)
                .message("Vendor created successfully")
                .data(vendorService.create(dto))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<VendorDto> get(@PathVariable Long id) {
        return ApiResponse.<VendorDto>builder()
                .success(true)
                .message("Vendor fetched successfully")
                .data(vendorService.get(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<VendorDto>> getAll() {
        return ApiResponse.<List<VendorDto>>builder()
                .success(true)
                .message("Vendors fetched successfully")
                .data(vendorService.getAll())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<VendorDto> update(
            @PathVariable Long id,
            @RequestBody VendorDto dto
    ) {
        return ApiResponse.<VendorDto>builder()
                .success(true)
                .message("Vendor updated successfully")
                .data(vendorService.update(id, dto))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        vendorService.delete(id);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Vendor deleted successfully")
                .build();
    }

    @GetMapping("/me")
    public ApiResponse<VendorResponseDto> getMyVendor() {
    	 System.out.println("aale");
        Long vendorId = VendorContext.getVendorId();
       
        return ApiResponse.success(
                vendorService.getById(vendorId)
        );
    }
    
   
}
