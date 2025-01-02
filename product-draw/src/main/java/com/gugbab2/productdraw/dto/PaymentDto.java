package com.gugbab2.productdraw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@AllArgsConstructor
public class PaymentDto {
    private String shippingAddress;
    private String paymentMethod;
    private double amount;
}
