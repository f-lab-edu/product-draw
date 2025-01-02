package com.gugbab2.productdraw.domain.service;

import com.gugbab2.productdraw.domain.entity.Entry;
import com.gugbab2.productdraw.domain.entity.Payment;
import com.gugbab2.productdraw.domain.entity.Product;
import com.gugbab2.productdraw.domain.repository.inmemory.EntryRepositoryImpl;
import com.gugbab2.productdraw.domain.repository.inmemory.PaymentRepositoryImpl;
import com.gugbab2.productdraw.domain.repository.inmemory.ProductRepositoryImpl;
import com.gugbab2.productdraw.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final EntryRepositoryImpl entryRepository;
    private final PaymentRepositoryImpl paymentRepository;
    private final ProductRepositoryImpl productRepository;

    public Entry createEntry(String productId, String shippingAddress, String paymentMethod) {
        Product product = productRepository.findById(productId);
        // 예외처리
        Entry entry = new Entry(UUID.randomUUID().toString(), product, shippingAddress, paymentMethod);
        return entryRepository.save(entry);
    }

    public Payment processPayment(String entryId, PaymentDto paymentDto) {
        Entry entry = entryRepository.findById(entryId);
        // 예외처리
        Payment payment = new Payment(UUID.randomUUID().toString(), entry, paymentDto.getAmount());
        payment.setSuccess(true);  // 결제 성공 처리
        return paymentRepository.save(payment);
    }

    public Entry checkWinner(String entryId) {
        Entry entry = entryRepository.findById(entryId);
        // 예외처리
        char isWinner = entry.getIsWinner();
        if (isWinner == '2') {
            refundPayment(entry);
        }
        return entry;
    }

    private void refundPayment(Entry entry) {
        Payment payment = paymentRepository.findById(entry.getId());
        // 예외처리
        if (payment != null && payment.isSuccess()) {
            payment.setSuccess(false);  // 환불 처리
            paymentRepository.save(payment);
        }
    }
}
