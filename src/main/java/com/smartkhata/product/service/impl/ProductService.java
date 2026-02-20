package com.smartkhata.product.service.impl;

import com.smartkhata.product.dto.ProductDto;
import com.smartkhata.product.dto.ProductUpdateDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface ProductService {

    // CREATE
    ProductDto create(ProductDto dto, Long vendorId);

    // UPDATE (vendor-safe)
    ProductDto update(Long id, ProductUpdateDto dto, Long vendorId);

    // DELETE (vendor-safe)
    void delete(Long id, Long vendorId);

    // PAGINATION
    Page<ProductDto> getProductsByVendor(
            Long vendorId,
            int page,
            int size
    );

    // PAGINATION + SORTING
    Page<ProductDto> getProductsWithSort(
            Long vendorId,
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    // SEARCH BY NAME
    Page<ProductDto> searchProducts(
            Long vendorId,
            String keyword,
            int page,
            int size
    );

    // FILTER BY PRICE RANGE
    Page<ProductDto> filterByPrice(
            Long vendorId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            int page,
            int size
    );

    // LOW STOCK (PAGINATED)
    Page<ProductDto> getLowStockProducts(
            Long vendorId,
            Integer threshold,
            int page,
            int size
    );
}
