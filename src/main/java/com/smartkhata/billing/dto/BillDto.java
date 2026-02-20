package com.smartkhata.billing.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BillDto {

    private Long billId;
    private String billNumber;
    private LocalDate billDate;

    private String customerName;
    private String customerMobile;

    private BigDecimal totalAmount;
    private String status;

    private List<BillItemResponseDto> items;
}
