package com.smartkhata.billing.service.impl;

import com.smartkhata.billing.dto.*;
import com.smartkhata.billing.entity.Bill;
import com.smartkhata.billing.entity.BillItem;
import com.smartkhata.billing.repository.BillRepository;
import com.smartkhata.product.entity.Product;
import com.smartkhata.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    // ---------------- CREATE ----------------

    @Transactional
    @Override
    public BillDto create(BillCreateDto dto, Long vendorId) {

        Bill bill = new Bill();
        bill.setVendorId(vendorId);
        bill.setBillNumber(dto.getBillNumber());
        bill.setBillDate(dto.getBillDate());
        bill.setCustomerName(dto.getCustomerName());
        bill.setCustomerMobile(dto.getCustomerMobile());
        bill.setStatus(dto.getStatus());

        BigDecimal total = BigDecimal.ZERO;
        List<BillItem> items = new ArrayList<>();

        for (BillItemCreateDto i : dto.getItems()) {

            Product product = productRepository
                    .findByIdAndVendorId(i.getProductId(), vendorId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // ✅ 1. STOCK VALIDATION
            if (product.getQuantity() < i.getQuantity()) {
                throw new RuntimeException(
                    "Insufficient stock for product: " + product.getName()
                );
            }

            // ✅ 2. REDUCE STOCK
            product.setQuantity(product.getQuantity() - i.getQuantity());
            productRepository.save(product);

            // ✅ 3. BUILD BILL ITEM
            BigDecimal lineTotal =
                    i.getUnitPriceSnapshot()
                            .multiply(BigDecimal.valueOf(i.getQuantity()));

            BillItem item = new BillItem();
            item.setBill(bill);
            item.setProductId(product.getId());
            item.setDescription(product.getName());
            item.setUnitPriceSnapshot(i.getUnitPriceSnapshot());
            item.setQuantity(i.getQuantity());
            item.setLineTotal(lineTotal);

            total = total.add(lineTotal);
            items.add(item);
        }

        bill.setTotalAmount(total);
        bill.setItems(items);

        Bill saved = billRepository.save(bill);
        return mapper.map(saved, BillDto.class);
    }


    // ---------------- READ ONE ----------------

    @Override
    public BillDto getById(Long billId, Long vendorId) {

        Bill bill = billRepository
                .findByBillIdAndVendorId(billId, vendorId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        return mapper.map(bill, BillDto.class);
    }

    // ---------------- READ ALL + SORT ----------------

    @Override
    public Page<BillDto> getBillsWithSort(
            Long vendorId, int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return billRepository.findAllByVendorId(vendorId, pageable)
                .map(b -> mapper.map(b, BillDto.class));
    }

    // ---------------- READ BY STATUS ----------------

    @Override
    public Page<BillDto> getBillsByStatus(
            Long vendorId, String status, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("billDate").descending());

        return billRepository.findAllByVendorIdAndStatus(vendorId, status, pageable)
                .map(b -> mapper.map(b, BillDto.class));
    }

    // ---------------- READ BY DATE ----------------

    @Override
    public Page<BillDto> getBillsByDateRange(
            Long vendorId, LocalDate start, LocalDate end, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("billDate").descending());

        return billRepository
                .findAllByVendorIdAndBillDateBetween(vendorId, start, end, pageable)
                .map(b -> mapper.map(b, BillDto.class));
    }

    // ---------------- UPDATE ----------------

    @Transactional
    @Override
    public BillDto update(Long billId, BillUpdateDto dto, Long vendorId) {

        Bill bill = billRepository
                .findByBillIdAndVendorId(billId, vendorId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        if ("PAID".equalsIgnoreCase(bill.getStatus()))
            throw new RuntimeException("Paid bill cannot be edited");

        bill.getItems().clear();

        BigDecimal total = BigDecimal.ZERO;

        for (BillItemCreateDto i : dto.getItems()) {

            BigDecimal lineTotal =
                    i.getUnitPriceSnapshot()
                            .multiply(BigDecimal.valueOf(i.getQuantity()));

            BillItem item = new BillItem();
            item.setBill(bill);
            item.setProductId(i.getProductId());
            item.setUnitPriceSnapshot(i.getUnitPriceSnapshot());
            item.setQuantity(i.getQuantity());
            item.setLineTotal(lineTotal);

            total = total.add(lineTotal);
            bill.getItems().add(item);
        }

        bill.setBillDate(dto.getBillDate());
        bill.setCustomerName(dto.getCustomerName());
        bill.setCustomerMobile(dto.getCustomerMobile());
        bill.setStatus(dto.getStatus());
        bill.setTotalAmount(total);

        return mapper.map(bill, BillDto.class);
    }

    // ---------------- DELETE ----------------

    @Override
    public void delete(Long billId, Long vendorId) {

        Bill bill = billRepository
                .findByBillIdAndVendorId(billId, vendorId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        if ("PAID".equalsIgnoreCase(bill.getStatus()))
            throw new RuntimeException("Paid bill cannot be deleted");

        billRepository.delete(bill);
    }

    // ---------------- CREDIT SETTLEMENT ----------------

    @Transactional
    @Override
    public void settleCredit(Long billId, Long vendorId) {

        Bill bill = billRepository
                .findByBillIdAndVendorId(billId, vendorId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        bill.setStatus("PAID");
    }

    // ---------------- PAYMENT GATEWAY HOOK ----------------

    @Transactional
    @Override
    public void markPaidAfterGateway(Long billId, String orderId) {

        Bill bill = billRepository
                .findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        bill.setStatus("PAID");
        bill.setLatestPaymentOrderId(orderId);
    }
}
