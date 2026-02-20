package com.smartkhata.billing.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bills")
@Getter @Setter
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    @Column(nullable = false)
    private Long vendorId;

    @Column(nullable = false, unique = true)
    private String billNumber;

    private LocalDate billDate;

    private String customerName;

    @Column(length = 10)
    private String customerMobile;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private String status; // PAID, CREDIT, PARTIAL

    @OneToMany(
    	    mappedBy = "bill",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true,
    	    fetch = FetchType.EAGER   // IMPORTANT
    	)
    	@JsonManagedReference
    	private List<BillItem> items = new ArrayList<>();

    // 💳 Razorpay linking
    private String latestPaymentOrderId;

    @CreationTimestamp
    
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

}
