package com.smartkhata.billing.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDto {

    private Long paymentId;
    private String billNumber;

    private BigDecimal amount;
    private String method;     // CASH, RAZORPAY
    private String status;     // SUCCESS, FAILED

    private LocalDateTime createdAt;
}
