package com.smartkhata.auth.repository;

import com.smartkhata.auth.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Optional<Vendor> findByPhone(String phone);

    Optional<Vendor> findByShopName(String shopName);

    boolean existsByPhone(String phone);
}
