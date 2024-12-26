package com.gugbab2.productdraw.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {
    private String id;
    private Entry entry;
    private double amount;
    private boolean success;

    public Payment(String id, Entry entry, double amount) {
        this.id = id;
        this.entry = entry;
        this.amount = amount;
        this.success = false;
    }
}
