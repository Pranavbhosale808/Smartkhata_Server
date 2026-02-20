package com.smartkhata.product.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long id;
    private Long vendorId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
