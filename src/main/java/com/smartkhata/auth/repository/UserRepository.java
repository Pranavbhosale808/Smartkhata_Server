package com.smartkhata.auth.repository;

import com.smartkhata.auth.entity.User;
import com.smartkhata.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByVendorId(Long vendorId);

    List<User> findByRole(Role role);
}
