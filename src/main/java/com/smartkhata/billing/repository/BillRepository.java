package com.smartkhata.billing.repository;

import com.smartkhata.billing.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {

    Optional<Bill> findByBillIdAndVendorId(Long billId, Long vendorId);

    Page<Bill> findAllByVendorId(Long vendorId, Pageable pageable);

    Page<Bill> findAllByVendorIdAndStatus(Long vendorId, String status, Pageable pageable);

    Page<Bill> findAllByVendorIdAndBillDateBetween(
            Long vendorId,
            LocalDate start,
            LocalDate end,
            Pageable pageable
    );
}
