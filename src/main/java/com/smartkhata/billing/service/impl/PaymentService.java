package com.smartkhata.billing.service.impl;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.smartkhata.billing.dto.CashPaymentRequest;
import com.smartkhata.billing.dto.PaymentResponseDto;
import com.smartkhata.billing.dto.RazorpayOrderRequest;
import com.smartkhata.billing.dto.RazorpayVerifyRequest;

public interface PaymentService {

    void recordCashPayment(CashPaymentRequest dto, Long vendorId);

    String createRazorpayOrder(RazorpayOrderRequest dto, Long vendorId);
    
    void verifyRazorpayPayment(RazorpayVerifyRequest dto, Long vendorId);
    
    Page<PaymentResponseDto> getPayments(
            Long vendorId,
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    Page<PaymentResponseDto> getPaymentsByDateRange(
            Long vendorId,
            LocalDate start,
            LocalDate end,
            int page,
            int size
    );

}
