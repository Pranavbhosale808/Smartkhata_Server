package com.smartkhata.dashboard.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStatsDto {

    private Long totalBills;
    private Long paidBills;
    private BigDecimal totalRevenue;
    private BigDecimal pendingCredit;
}
