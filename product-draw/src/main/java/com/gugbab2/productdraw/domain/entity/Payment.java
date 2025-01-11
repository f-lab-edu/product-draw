package com.gugbab2.productdraw.domain.entity;

import com.gugbab2.productdraw.domain.vo.PaymentMethod;
import com.gugbab2.productdraw.domain.vo.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Payment {
    private String id;
    private String drawId;
    private PaymentMethod paymentMethod;
    private long amount;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;

    public Payment(String drawId, PaymentMethod paymentMethod, long amount) {
        this.id = UUID.randomUUID().toString();
        this.drawId = drawId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.paymentStatus = PaymentStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

}
