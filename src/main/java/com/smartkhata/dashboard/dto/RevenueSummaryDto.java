package com.smartkhata.dashboard.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueSummaryDto {

    private BigDecimal amount;        // total revenue
    private Long transactionCount;    // number of payments
}
