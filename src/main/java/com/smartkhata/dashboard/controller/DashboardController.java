package com.smartkhata.dashboard.controller;

import com.smartkhata.common.response.ApiResponse;
import com.smartkhata.common.security.VendorContext;
import com.smartkhata.dashboard.dto.*;
import com.smartkhata.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService service;

    // ================= BASIC STATS =================

    @GetMapping("/stats")
    public ApiResponse<DashboardStatsDto> stats() {
        Long vendorId = VendorContext.getVendorId();
        return ApiResponse.success(service.getStats(vendorId));
    }

    // ================= REVENUE CHART =================

    @GetMapping("/revenue")
    public ApiResponse<List<RevenueChartDto>> revenue(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate to
    ) {
        Long vendorId = VendorContext.getVendorId();
        return ApiResponse.success(
                service.getRevenueChart(vendorId, from, to)
        );
    }

    // ================= TOP PRODUCTS =================

    @GetMapping("/top-products")
    public ApiResponse<List<TopProductDto>> topProducts() {
        Long vendorId = VendorContext.getVendorId();
        return ApiResponse.success(service.getTopProducts(vendorId));
    }

    // ================= PAYMENT METHODS =================

    @GetMapping("/payment-methods")
    public ApiResponse<List<PaymentMethodDto>> paymentMethods() {
        Long vendorId = VendorContext.getVendorId();
        return ApiResponse.success(service.getPaymentMethods(vendorId));
    }

    // ================= ALERTS =================

    @GetMapping("/alerts")
    public ApiResponse<List<AlertDto>> alerts() {
        Long vendorId = VendorContext.getVendorId();
        return ApiResponse.success(service.getAlerts(vendorId));
    }

    // ================= QUICK METRICS (optional high-value APIs) =================

//    // today’s revenue
//    @GetMapping("/today-revenue")
//    public ApiResponse<RevenueSummaryDto> todayRevenue() {
//        Long vendorId = VendorContext.getVendorId();
//        return ApiResponse.success(service.getTodayRevenue(vendorId));
//    }
//
//    // this month’s revenue
//    @GetMapping("/month-revenue")
//    public ApiResponse<RevenueSummaryDto> monthRevenue() {
//        Long vendorId = VendorContext.getVendorId();
//        return ApiResponse.success(service.getMonthRevenue(vendorId));
//    }
//
//    // credit risk
//    @GetMapping("/credit-summary")
//    public ApiResponse<CreditSummaryDto> creditSummary() {
//        Long vendorId = VendorContext.getVendorId();
//        return ApiResponse.success(service.getCreditSummary(vendorId));
//    }
}
