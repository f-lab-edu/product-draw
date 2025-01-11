package com.gugbab2.productdraw.domain.repository;

import com.gugbab2.productdraw.domain.entity.Payment;

import java.util.List;

public interface PaymentRepository {
    Payment save(Payment payment);
    Payment findById(String id);
    Payment findAllByDrawId(String DrawId);
    Payment updatePaymentStatus(String id, Payment payment);
}
