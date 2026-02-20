package com.smartkhata.billing.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class RazorpayOrderRequest {
    private Long billId;
    private BigDecimal amount;
}
