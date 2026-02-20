package com.smartkhata.billing.repository;

import com.smartkhata.billing.entity.Payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByBill_BillIdAndVendorId(Long billId, Long vendorId);
    

    Page<Payment> findByVendorId(Long vendorId, Pageable pageable);

    Page<Payment> findByVendorIdAndCreatedAtBetween(
            Long vendorId,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );
}
