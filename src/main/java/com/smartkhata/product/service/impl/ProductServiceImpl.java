package com.smartkhata.product.service.impl;

import com.smartkhata.common.exception.ResourceNotFoundException;
import com.smartkhata.product.dto.ProductDto;
import com.smartkhata.product.dto.ProductUpdateDto;
import com.smartkhata.product.entity.Product;
import com.smartkhata.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    // CREATE
    @Override
    public ProductDto create(ProductDto dto, Long vendorId) {
        Product product = mapper.map(dto, Product.class);
        product.setVendorId(vendorId);

        Product saved = productRepository.save(product);
        return mapper.map(saved, ProductDto.class);
    }

    // UPDATE (vendor-safe)
    @Override
    public ProductDto update(Long id, ProductUpdateDto dto, Long vendorId) {
        Product product = productRepository
                .findByIdAndVendorId(id, vendorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());

        return mapper.map(productRepository.save(product), ProductDto.class);
    }

    // DELETE (vendor-safe)
    @Override
    public void delete(Long id, Long vendorId) {
        Product product = productRepository
                .findByIdAndVendorId(id, vendorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);
    }

    // PAGINATION
    @Override
    public Page<ProductDto> getProductsByVendor(
            Long vendorId,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return productRepository
                .findByVendorId(vendorId, pageable)
                .map(p -> mapper.map(p, ProductDto.class));
    }

    // PAGINATION + SORT
    @Override
    public Page<ProductDto> getProductsWithSort(
            Long vendorId,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository
                .findByVendorId(vendorId, pageable)
                .map(p -> mapper.map(p, ProductDto.class));
    }

    // SEARCH
    @Override
    public Page<ProductDto> searchProducts(
            Long vendorId,
            String keyword,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return productRepository
                .findByVendorIdAndNameContainingIgnoreCase(
                        vendorId, keyword, pageable
                )
                .map(p -> mapper.map(p, ProductDto.class));
    }

    // PRICE RANGE
    @Override
    public Page<ProductDto> filterByPrice(
            Long vendorId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return productRepository
                .findByVendorIdAndPriceBetween(
                        vendorId, minPrice, maxPrice, pageable
                )
                .map(p -> mapper.map(p, ProductDto.class));
    }

    // LOW STOCK
    @Override
    public Page<ProductDto> getLowStockProducts(
            Long vendorId,
            Integer threshold,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return productRepository
                .findLowStockProducts(vendorId, threshold, pageable)
                .map(p -> mapper.map(p, ProductDto.class));
    }
}
