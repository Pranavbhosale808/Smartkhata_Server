package com.smartkhata.product.controller;

import com.smartkhata.common.response.ApiResponse;
import com.smartkhata.common.response.PageResponse;
import com.smartkhata.common.security.VendorContext;
import com.smartkhata.product.dto.ProductDto;
import com.smartkhata.product.dto.ProductUpdateDto;
import com.smartkhata.product.service.impl.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 🔹 1. CREATE PRODUCT
    @PostMapping
    public ApiResponse<ProductDto> createProduct(
            @Valid @RequestBody ProductDto dto
    ) {
        Long vendorId = VendorContext.getVendorId();
        return ApiResponse.success(
                productService.create(dto, vendorId)
        );
    }

    // 🔹 2. GET PRODUCTS (Pagination + Sorting)
    @GetMapping
    public ApiResponse<PageResponse<ProductDto>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Long vendorId = VendorContext.getVendorId();

        Page<ProductDto> productPage =
                productService.getProductsWithSort(
                        vendorId, page, size, sortBy, sortDir
                );

        return buildPageResponse(productPage, "Products fetched successfully");
    }

    // 🔹 3. SEARCH PRODUCT BY NAME
    @GetMapping("/search")
    public ApiResponse<PageResponse<ProductDto>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long vendorId = VendorContext.getVendorId();

        Page<ProductDto> productPage =
                productService.searchProducts(
                        vendorId, keyword, page, size
                );

        return buildPageResponse(productPage, "Products searched successfully");
    }

    // 🔹 4. FILTER BY PRICE RANGE
    @GetMapping("/price-range")
    public ApiResponse<PageResponse<ProductDto>> filterByPrice(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long vendorId = VendorContext.getVendorId();

        Page<ProductDto> productPage =
                productService.filterByPrice(
                        vendorId, minPrice, maxPrice, page, size
                );

        return buildPageResponse(productPage, "Products filtered by price");
    }

    // 🔹 5. LOW STOCK PRODUCTS
    @GetMapping("/low-stock")
    public ApiResponse<PageResponse<ProductDto>> getLowStockProducts(
            @RequestParam(defaultValue = "5") int threshold,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long vendorId = VendorContext.getVendorId();

        Page<ProductDto> productPage =
                productService.getLowStockProducts(
                        vendorId, threshold, page, size
                );

        return buildPageResponse(productPage, "Low stock products fetched");
    }

    // 🔹 6. UPDATE PRODUCT (vendor-safe)
    @PutMapping("/{id}")
    public ApiResponse<ProductDto> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateDto dto
    ) {
        Long vendorId = VendorContext.getVendorId();

        return ApiResponse.success(
                productService.update(id, dto, vendorId)
        );
    }

    // 🔹 7. DELETE PRODUCT (vendor-safe)
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(
            @PathVariable Long id
    ) {
        Long vendorId = VendorContext.getVendorId();
        productService.delete(id, vendorId);
        return ApiResponse.success(null);
    }

    // 🔹 COMMON METHOD TO BUILD PAGE RESPONSE
    private ApiResponse<PageResponse<ProductDto>> buildPageResponse(
            Page<ProductDto> page,
            String message
    ) {
        PageResponse<ProductDto> response =
                PageResponse.<ProductDto>builder()
                        .content(page.getContent())
                        .page(page.getNumber())
                        .size(page.getSize())
                        .totalElements(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .last(page.isLast())
                        .build();

        return ApiResponse.<PageResponse<ProductDto>>builder()
                .success(true)
                .message(message)
                .data(response)
                .build();
    }
}
