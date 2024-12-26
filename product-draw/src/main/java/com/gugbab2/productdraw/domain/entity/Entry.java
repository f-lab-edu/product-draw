package com.gugbab2.productdraw.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Entry {
    private String id;
    private Product product;
    private String shippingAddress;
    private String paymentMethod;
    private boolean isPaid;
    private char isWinner;

    public Entry(String id, Product product, String shippingAddress, String paymentMethod) {
        this.id = id;
        this.product = product;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.isPaid = false;
        this.isWinner = '0';    // 0 : 대기, 1 : 당첨, 2 : 미당첨
    }
}
