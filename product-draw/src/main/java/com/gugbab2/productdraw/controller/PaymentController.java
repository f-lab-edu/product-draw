package com.gugbab2.productdraw.controller;

import com.gugbab2.productdraw.domain.entity.Payment;
import com.gugbab2.productdraw.domain.service.PaymentService;
import com.gugbab2.productdraw.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 요청
    @PostMapping("/request")
    public ResponseEntity<?> requestPayment(@RequestBody PaymentDto.RequestPaymentDto requestPaymentDto) {
        try{
            Payment payment = paymentService.requestPayment(requestPaymentDto.getDrawId(), requestPaymentDto.getPaymentMethod(), requestPaymentDto.getAmount());
            return new ResponseEntity<>(payment, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 결제 환불 요청
//    @PostMapping("/refund")
//    public ResponseEntity<?> refundPayment(@RequestBody PaymentDto.RefundPaymentDto refundPaymentDto) {
//        try{
//            Payment payment = paymentService.requestPayment(requestPaymentDto.getDrawId(), requestPaymentDto.getPaymentMethod(), requestPaymentDto.getAmount());
//            return new ResponseEntity<>(payment, HttpStatus.CREATED);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
}
