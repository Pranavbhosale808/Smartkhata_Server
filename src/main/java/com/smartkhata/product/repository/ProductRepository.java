package com.smartkhata.product.repository;

import com.smartkhata.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 🔹 PAGINATION
    Page<Product> findByVendorId(
            Long vendorId,
            Pageable pageable
    );

    // 🔹 FIND SINGLE PRODUCT (vendor-safe)
    Optional<Product> findByIdAndVendorId(
            Long id,
            Long vendorId
    );

    // 🔹 SEARCH BY NAME
    Page<Product> findByVendorIdAndNameContainingIgnoreCase(
            Long vendorId,
            String name,
            Pageable pageable
    );

    // 🔹 FILTER BY PRICE RANGE
    Page<Product> findByVendorIdAndPriceBetween(
            Long vendorId,
            BigDecimal min,
            BigDecimal max,
            Pageable pageable
    );

    // 🔹 LOW STOCK
    @Query("""
        SELECT p FROM Product p
        WHERE p.vendorId = :vendorId
          AND p.quantity <= :threshold
    """)
    Page<Product> findLowStockProducts(
            Long vendorId,
            Integer threshold,
            Pageable pageable
    );

    // 🔹 DELETE (vendor-safe)
    void deleteByIdAndVendorId(
            Long id,
            Long vendorId
    );
}
