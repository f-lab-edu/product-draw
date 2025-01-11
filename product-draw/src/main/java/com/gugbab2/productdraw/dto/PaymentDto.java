package com.gugbab2.productdraw.dto;

import com.gugbab2.productdraw.domain.vo.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class PaymentDto {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class RequestPaymentDto{
        private String drawId;
        private PaymentMethod paymentMethod;
        private long amount;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class RefundPaymentDto{
        private String paymentId;
        private long amount;
    }

}
