package com.gugbab2.productdraw.domain.repository.inmemory;

import com.gugbab2.productdraw.domain.entity.Payment;
import com.gugbab2.productdraw.domain.repository.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private final Map<String, Payment> paymentDatabase = new HashMap<>();

    @Override
    public Payment save(Payment payment) {
        if (payment.getId() == null) {
            // 예외처리
        }
        paymentDatabase.put(payment.getId(), payment);
        return payment;
    }

    @Override
    public Payment findById(String id) {
        return paymentDatabase.get(id);
    }
}
