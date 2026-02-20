package com.smartkhata.dashboard.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueChartDto {

    private String label;       // e.g. "Jan", "Feb", "Mar" OR "2026-01-28"
    private BigDecimal amount;  // total revenue for that period
}
