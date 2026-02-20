package com.smartkhata.billing.service.impl;

import com.smartkhata.billing.dto.CashPaymentRequest;
import com.smartkhata.billing.dto.PaymentResponseDto;
import com.smartkhata.billing.dto.RazorpayOrderRequest;
import com.smartkhata.billing.dto.RazorpayVerifyRequest;
import com.smartkhata.billing.entity.Bill;
import com.smartkhata.billing.entity.Payment;
import com.smartkhata.billing.repository.BillRepository;
import com.smartkhata.billing.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import org.springframework.data.domain.*;


@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final BillRepository billRepository;
    private final PaymentRepository paymentRepository;
    private final RazorpayClient razorpayClient;

    @Value("${razorpay.key.secret}")
    private String razorpaySecret;


    @Override
    public void recordCashPayment(CashPaymentRequest dto, Long vendorId) {

        Bill bill = billRepository
                .findByBillIdAndVendorId(dto.getBillId(), vendorId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        Payment payment = new Payment();
        payment.setVendorId(vendorId);
        payment.setBill(bill);                // 🔥 NEVER NULL
        payment.setAmount(dto.getAmount());
        payment.setMethod("CASH");
        payment.setStatus("SUCCESS");

        paymentRepository.save(payment);

        bill.setStatus("PAID");
        billRepository.save(bill);
    }
    
    private String hmacSha256(String data, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(), "HmacSHA256"));
        byte[] rawHmac = mac.doFinal(data.getBytes());

        StringBuilder hex = new StringBuilder(2 * rawHmac.length);
        for (byte b : rawHmac) {
            String s = Integer.toHexString(0xff & b);
            if (s.length() == 1) hex.append('0');
            hex.append(s);
        }
        return hex.toString();
    }


    @Override
    public void verifyRazorpayPayment(RazorpayVerifyRequest dto, Long vendorId) {

        Bill bill = billRepository
                .findByBillIdAndVendorId(dto.getBillId(), vendorId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        try {
            String data = dto.getRazorpayOrderId() + "|" + dto.getRazorpayPaymentId();
            String generatedSignature = hmacSha256(data, razorpaySecret);

            if (!generatedSignature.equals(dto.getRazorpaySignature())) {
                throw new RuntimeException("Invalid Razorpay signature");
            }

            Payment payment = new Payment();
            payment.setVendorId(vendorId);
            payment.setBill(bill);
            payment.setAmount(bill.getTotalAmount());
            payment.setMethod("RAZORPAY");
            payment.setRazorpayOrderId(dto.getRazorpayOrderId());
            payment.setRazorpayPaymentId(dto.getRazorpayPaymentId());
            payment.setRazorpaySignature(dto.getRazorpaySignature());
            payment.setStatus("SUCCESS");

            paymentRepository.save(payment);

            bill.setStatus("PAID");
            billRepository.save(bill);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Razorpay verification failed");
        }
    }


    
    @Override
    public String createRazorpayOrder(RazorpayOrderRequest dto, Long vendorId) {

        Bill bill = billRepository
                .findByBillIdAndVendorId(dto.getBillId(), vendorId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        try {
            JSONObject options = new JSONObject();
            options.put("amount", dto.getAmount().multiply(new BigDecimal("100"))); 
            options.put("currency", "INR");
            options.put("receipt", "SK_BILL_" + bill.getBillId());

            Order order = razorpayClient.orders.create(options);

            bill.setLatestPaymentOrderId(order.get("id"));
            billRepository.save(bill);

            return order.get("id");

        } catch (Exception e) {
            throw new RuntimeException("Razorpay order creation failed");
        }
    }
    

    @Override
    public Page<PaymentResponseDto> getPayments(
            Long vendorId, int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return paymentRepository.findByVendorId(vendorId, pageable)
                .map(this::map);
    }

    @Override
    public Page<PaymentResponseDto> getPaymentsByDateRange(
            Long vendorId, LocalDate start, LocalDate end, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        LocalDateTime from = start.atStartOfDay();
        LocalDateTime to = end.atTime(23, 59, 59);

        return paymentRepository
                .findByVendorIdAndCreatedAtBetween(vendorId, from, to, pageable)
                .map(this::map);
    }

    /* ---------------- MAPPER ---------------- */

    private PaymentResponseDto map(Payment p) {
        return PaymentResponseDto.builder()
                .paymentId(p.getPaymentId())
                .billNumber(p.getBill().getBillNumber())
                .amount(p.getAmount())
                .method(p.getMethod())
                .status(p.getStatus())
                .createdAt(p.getCreatedAt())
                .build();
    }

}
