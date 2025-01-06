package com.gugbab2.productdraw.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Entrant {
    private String id;
    private String shippingAddress;
    private String paymentMethod;

    public Entrant(String shippingAddress, String paymentMethod) {
        this.id = UUID.randomUUID().toString();
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }

}

