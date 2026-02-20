package com.smartkhata.billing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RazorpayVerifyRequest {
    private Long billId;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
}
