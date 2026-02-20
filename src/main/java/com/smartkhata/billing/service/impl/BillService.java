package com.smartkhata.billing.service.impl;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.smartkhata.billing.dto.BillCreateDto;
import com.smartkhata.billing.dto.BillDto;
import com.smartkhata.billing.dto.BillUpdateDto;

public interface BillService {

    // -------- CREATE --------
    BillDto create(BillCreateDto dto, Long vendorId);

    // -------- READ --------
    BillDto getById(Long billId, Long vendorId);

    Page<BillDto> getBillsWithSort(
            Long vendorId,
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    Page<BillDto> getBillsByStatus(
            Long vendorId,
            String status,
            int page,
            int size
    );

    Page<BillDto> getBillsByDateRange(
            Long vendorId,
            LocalDate start,
            LocalDate end,
            int page,
            int size
    );

    // -------- UPDATE --------
    BillDto update(Long billId, BillUpdateDto dto, Long vendorId);

    // -------- DELETE --------
    void delete(Long billId, Long vendorId);

    // -------- CREDIT FLOW --------
    void settleCredit(Long billId, Long vendorId);

    // -------- PAYMENT GATEWAY HOOK --------
    void markPaidAfterGateway(Long billId, String orderId);
    
    
}

