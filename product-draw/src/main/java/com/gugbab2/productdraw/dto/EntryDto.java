package com.gugbab2.productdraw.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntryDto {
    private String productId;
    private String shippingAddress;
    private String paymentMethod;
}
