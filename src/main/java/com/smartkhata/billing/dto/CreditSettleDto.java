package com.smartkhata.billing.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreditSettleDto {

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal paidAmount;

    private String note;
}

