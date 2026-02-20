package com.smartkhata.dashboard.service;

import com.smartkhata.dashboard.dto.*;
import com.smartkhata.dashboard.repository.DashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository repo;

    @Override
    public DashboardStatsDto getStats(Long vendorId) {
        return DashboardStatsDto.builder()
                .totalBills(repo.totalBills(vendorId))
                .paidBills(repo.paidBills(vendorId))
                .totalRevenue(repo.totalRevenue(vendorId))   // ✅ from payments
                .pendingCredit(repo.pendingCredit(vendorId))
                .build();
    }

    @Override
    public List<TopProductDto> getTopProducts(Long vendorId) {
        return repo.topProducts(vendorId)
                .stream()
                .map((Object[] r) -> TopProductDto.builder()
                        .productName((String) r[0])
                        .quantity((Long) r[1])
                        .build())
                .collect(java.util.stream.Collectors.toList());
    }


    @Override
    public List<PaymentMethodDto> getPaymentMethods(Long vendorId) {
        return repo.paymentMethods(vendorId)
                .stream()
                .map(r -> PaymentMethodDto.builder()
                        .name(r[0].toString())
                        .value((Long) r[1])
                        .build())
                .toList();
    }

    @Override
    public List<RevenueChartDto> getRevenueChart(Long vendorId, LocalDate from, LocalDate to) {

        List<Object[]> rawData = repo.revenueChart(vendorId, from, to);

        return rawData.stream()
                .map((Object[] r) -> {
                    String label = String.valueOf(r[0]);
                    BigDecimal amount = (BigDecimal) r[1];

                    return RevenueChartDto.builder()
                            .label(label)
                            .amount(amount)
                            .build();
                })
                .toList();
    }



    @Override
    public List<AlertDto> getAlerts(Long vendorId) {
        return List.of(
                new AlertDto("Pending credit requires follow-up"),
                new AlertDto("Cash vs Online ratio available"),
                new AlertDto("Revenue is now payment-verified")
        );
    }
}
