package com.smartkhata.dashboard.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditSummaryDto {

    private BigDecimal pendingAmount;  // total credit amount
    private Long creditBills;          // number of CREDIT bills
}
