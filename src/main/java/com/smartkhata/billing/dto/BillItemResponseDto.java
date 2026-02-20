package com.smartkhata.billing.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BillItemResponseDto {

    private Long productId;
    private String description;
    private BigDecimal unitPriceSnapshot;
    private Integer quantity;
    private BigDecimal lineTotal;
}
