package com.gugbab2.productdraw.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Draw {
    private String id;
    private String entrantId;
    private String productId;
    private String paymentId;
    private boolean isWinner;

    public Draw(String entrantId, String productId) {
        this.id = UUID.randomUUID().toString();
        this.entrantId = entrantId;
        this.productId = productId;
        this.paymentId = "";
        this.isWinner = false;
    }
}
