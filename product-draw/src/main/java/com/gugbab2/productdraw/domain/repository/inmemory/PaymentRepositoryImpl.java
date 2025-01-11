package com.gugbab2.productdraw.domain.repository.inmemory;

import com.gugbab2.productdraw.domain.entity.Payment;
import com.gugbab2.productdraw.domain.repository.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private final Map<String, Payment> paymentsDatabase = new HashMap<>();

    @Override
    public Payment save(Payment payment) {
        paymentsDatabase.put(payment.getId(), payment);
        return payment;
    }

    @Override
    public Payment findById(String id) {
        return paymentsDatabase.get(id);
    }

    @Override
    public Payment findAllByDrawId(String DrawId) {
        for(Payment payment : paymentsDatabase.values()) {
            if(payment.getDrawId().equals(DrawId)) {
                return payment;
            }
        }
        return null;
    }

    public Payment updatePaymentStatus(String id, Payment payment) {
        paymentsDatabase.put(id, payment);
        return payment;
    }

}
