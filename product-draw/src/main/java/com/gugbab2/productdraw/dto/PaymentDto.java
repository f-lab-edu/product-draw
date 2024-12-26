package com.gugbab2.productdraw.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDto {
    private String shippingAddress;
    private String paymentMethod;
    private double amount;
}
