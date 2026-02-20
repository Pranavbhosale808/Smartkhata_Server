package com.smartkhata.billing.controller;

import com.smartkhata.billing.dto.*;
import com.smartkhata.billing.service.impl.BillService;
import com.smartkhata.common.response.ApiResponse;
import com.smartkhata.common.response.PageResponse;
import com.smartkhata.common.security.VendorContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @PostMapping
    public ApiResponse<BillDto> create(@Valid @RequestBody BillCreateDto dto) {
        return ApiResponse.success(
                billService.create(dto, VendorContext.getVendorId())
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<BillDto> getById(@PathVariable Long id) {
        return ApiResponse.success(
                billService.getById(id, VendorContext.getVendorId())
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<BillDto> update(
            @PathVariable Long id,
            @Valid @RequestBody BillUpdateDto dto
    ) {
        return ApiResponse.success(
                billService.update(id, dto, VendorContext.getVendorId())
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        billService.delete(id, VendorContext.getVendorId());
        return ApiResponse.success(null);
    }

    @GetMapping
    public ApiResponse<PageResponse<BillDto>> getBills(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "billDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Page<BillDto> billPage =
                billService.getBillsWithSort(
                        VendorContext.getVendorId(), page, size, sortBy, sortDir
                );

        return buildPageResponse(billPage, "Bills fetched");
    }

    @GetMapping("/status")
    public ApiResponse<PageResponse<BillDto>> getByStatus(
            @RequestParam String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<BillDto> billPage =
                billService.getBillsByStatus(
                        VendorContext.getVendorId(), status, page, size
                );

        return buildPageResponse(billPage, "Bills by status fetched");
    }

    @GetMapping("/date-range")
    public ApiResponse<PageResponse<BillDto>> getByDateRange(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<BillDto> billPage =
                billService.getBillsByDateRange(
                        VendorContext.getVendorId(), start, end, page, size
                );

        return buildPageResponse(billPage, "Bills by date fetched");
    }

    // ⚠️ INTERNAL — called by PaymentService after Razorpay verification
    @PostMapping("/{id}/mark-paid")
    public ApiResponse<String> markPaid(
            @PathVariable Long id,
            @RequestParam String orderId
    ) {
        billService.markPaidAfterGateway(id, orderId);
        return ApiResponse.success("Bill marked PAID");
    }

    private ApiResponse<PageResponse<BillDto>> buildPageResponse(
            Page<BillDto> page, String message) {

        PageResponse<BillDto> response =
                PageResponse.<BillDto>builder()
                        .content(page.getContent())
                        .page(page.getNumber())
                        .size(page.getSize())
                        .totalElements(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .last(page.isLast())
                        .build();

        return ApiResponse.<PageResponse<BillDto>>builder()
                .success(true)
                .message(message)
                .data(response)
                .build();
    }
}
