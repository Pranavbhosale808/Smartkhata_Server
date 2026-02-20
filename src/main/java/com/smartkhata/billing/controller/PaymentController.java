package com.smartkhata.billing.controller;

import com.smartkhata.billing.dto.CashPaymentRequest;
import com.smartkhata.billing.dto.PaymentResponseDto;
import com.smartkhata.billing.dto.RazorpayOrderRequest;
import com.smartkhata.billing.dto.RazorpayVerifyRequest;
import com.smartkhata.billing.service.impl.PaymentService;
import com.smartkhata.common.response.ApiResponse;
import com.smartkhata.common.response.PageResponse;
import com.smartkhata.common.security.VendorContext;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/cash")
    public ApiResponse<Void> cash(@RequestBody CashPaymentRequest dto) {
        paymentService.recordCashPayment(dto, VendorContext.getVendorId());
        return ApiResponse.success(null);
    }

    @PostMapping("/razorpay/verify")
    public ApiResponse<Void> verify(@RequestBody RazorpayVerifyRequest dto) {
        paymentService.verifyRazorpayPayment(dto, VendorContext.getVendorId());
        return ApiResponse.success(null);
    }
    
    @PostMapping("/razorpay/order")
    public ApiResponse<String> createOrder(@RequestBody RazorpayOrderRequest dto) {
        String orderId = paymentService.createRazorpayOrder(dto, VendorContext.getVendorId());
        return ApiResponse.success(orderId);
    }
    
    /* ---------------- LIST ---------------- */

    @GetMapping
    public ApiResponse<PageResponse<PaymentResponseDto>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Long vendorId = VendorContext.getVendorId();

        Page<PaymentResponseDto> result =
        		paymentService.getPayments(vendorId, page, size, sortBy, sortDir);

        PageResponse<PaymentResponseDto> response =
                PageResponse.<PaymentResponseDto>builder()
                        .content(result.getContent())
                        .page(result.getNumber())
                        .size(result.getSize())
                        .totalElements(result.getTotalElements())
                        .totalPages(result.getTotalPages())
                        .last(result.isLast())
                        .build();

        return ApiResponse.success(response);
    }

    @GetMapping("/date-range")
    public ApiResponse<PageResponse<PaymentResponseDto>> byDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long vendorId = VendorContext.getVendorId();

        Page<PaymentResponseDto> result =
        		paymentService.getPaymentsByDateRange(vendorId, start, end, page, size);

        PageResponse<PaymentResponseDto> response =
                PageResponse.<PaymentResponseDto>builder()
                        .content(result.getContent())
                        .page(result.getNumber())
                        .size(result.getSize())
                        .totalElements(result.getTotalElements())
                        .totalPages(result.getTotalPages())
                        .last(result.isLast())
                        .build();

        return ApiResponse.success(response);
    }


}
