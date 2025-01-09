package com.gugbab2.productdraw.domain.service;

import com.gugbab2.productdraw.domain.entity.Draw;
import com.gugbab2.productdraw.domain.entity.Payment;
import com.gugbab2.productdraw.domain.entity.Product;
import com.gugbab2.productdraw.domain.repository.inmemory.DrawRepositoryImpl;
import com.gugbab2.productdraw.domain.repository.inmemory.PaymentRepositoryImpl;
import com.gugbab2.productdraw.domain.repository.inmemory.ProductRepositoryImpl;
import com.gugbab2.productdraw.domain.vo.PaymentMethod;
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

        // 결제 처리(카드사 API 호출)

        // 결제 완료 시 비동기로 결제 알림

        // 결제 완료 후 결제 정보 DB 저장
        Payment payment = new Payment(drawId, paymentMethod, amount);
        return paymentRepository.save(payment);
    }

}
