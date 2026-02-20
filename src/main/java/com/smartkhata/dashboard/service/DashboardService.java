package com.smartkhata.dashboard.service;

import com.smartkhata.dashboard.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface DashboardService {

    DashboardStatsDto getStats(Long vendorId);

    List<TopProductDto> getTopProducts(Long vendorId);

    List<PaymentMethodDto> getPaymentMethods(Long vendorId);

    List<RevenueChartDto> getRevenueChart(Long vendorId, LocalDate from, LocalDate to);

    List<AlertDto> getAlerts(Long vendorId);
}
