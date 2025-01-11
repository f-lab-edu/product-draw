package com.gugbab2.productdraw.domain.service;

import com.gugbab2.productdraw.domain.entity.Draw;
import com.gugbab2.productdraw.domain.entity.Payment;
import com.gugbab2.productdraw.domain.entity.Product;
import com.gugbab2.productdraw.domain.repository.inmemory.DrawRepositoryImpl;
import com.gugbab2.productdraw.domain.repository.inmemory.PaymentRepositoryImpl;
import com.gugbab2.productdraw.domain.repository.inmemory.ProductRepositoryImpl;
import com.gugbab2.productdraw.domain.vo.PaymentMethod;
import com.gugbab2.productdraw.domain.vo.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final DrawRepositoryImpl drawRepository;
    private final ProductRepositoryImpl productRepository;
    private final PaymentRepositoryImpl paymentRepository;

    public Payment requestPayment(String drawId, PaymentMethod paymentMethod, long amount) {

        // 사용자 인증 상태 확인(사용자 인증 개념 추가 후 적용 가능)

        // 응모 당첨 여부 확인
        Draw draw = drawRepository.findById(drawId);
        if (!draw.isWinner()) {
            throw new RuntimeException("entrant is not winner");
        }

        // 결제 정보 검증(제품 가격 체크)
        Product product = productRepository.findById(draw.getProductId());
        if(product.getPrice() != amount) {
            throw new RuntimeException("product price is not equal to amount");
        }

        // 중복 결제 여부 검증
        Payment findPayment = paymentRepository.findAllByDrawId(drawId);
        if (findPayment != null) {
            throw new RuntimeException("payment already exists");
        }

        // 결제 저장
        Payment payment = paymentRepository.save(new Payment(drawId, paymentMethod, amount));

        // 결제 처리(카드사 API 호출)

        // 결제 완료 시 결제 완료 여부 수정
        payment.setPaymentStatus(PaymentStatus.COMPLETED);

        // 결제 완료 시 비동기로 결제 알림

        return payment;
    }

    public Payment refundPayment(String drawId, String paymentId, long amont) {

        // 사용자 인증 상태 확인

        // 결제 내역 조회 후 환불 가능여부 확인
        Payment payment = getPayment(paymentId, amont);

        // 환불 처리(카드사 API)

        // 결제 상태 환불 변경
        payment.setPaymentStatus(PaymentStatus.REFUNDED);

        // 비동기로 환불 알림

        return paymentRepository.updatePaymentStatus(payment.getId(), payment);
    }

    private Payment getPayment(String paymentId, long amont) {

        Payment payment = paymentRepository.findById(paymentId);

        // 결제 존재여부
        if (payment == null){
            throw new RuntimeException("payment not found");
        }
        // 결제 금액, 환불 금액 일치 여부
        if (payment.getAmount() != amont) {
            throw new RuntimeException("payment amount is not equal to parameter(: amont)");
        }
        // 결제 상태 확인(미결제)
        if (payment.getPaymentStatus().equals(PaymentStatus.PENDING)){
            throw new RuntimeException("payment is not paid");
        }
        // 결제 상태 확인(환불 완료)
        if (payment.getPaymentStatus().equals(PaymentStatus.REFUNDED)) {
            throw new RuntimeException("payment is refunded");
        }
        return payment;
    }

}
