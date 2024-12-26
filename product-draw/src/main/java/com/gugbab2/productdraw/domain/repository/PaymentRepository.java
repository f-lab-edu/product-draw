package com.gugbab2.productdraw.domain.repository;

import com.gugbab2.productdraw.domain.entity.Payment;

public interface PaymentRepository {
    Payment save(Payment payment);
    Payment findById(String id);
}
