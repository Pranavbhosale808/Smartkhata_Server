package com.smartkhata.billing.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RazorpayOrderResponse {

    private String orderId;
    private String currency;
    private Long amount;
    private String razorpayKey;
}
