package com.smartkhata.billing.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BillItemCreateDto {

    @NotNull
    private Long productId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal unitPriceSnapshot;

    @Min(1)
    private Integer quantity;
}

