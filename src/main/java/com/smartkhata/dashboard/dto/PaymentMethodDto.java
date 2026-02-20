package com.smartkhata.dashboard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethodDto {

    private String name;   // CASH, RAZORPAY
    private Long value;    // count
}
