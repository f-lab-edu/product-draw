package com.gugbab2.productdraw.domain.service;

import com.gugbab2.productdraw.domain.entity.Draw;
import com.gugbab2.productdraw.domain.entity.Product;
import com.gugbab2.productdraw.domain.repository.inmemory.DrawRepositoryImpl;
import com.gugbab2.productdraw.domain.repository.inmemory.ProductRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DrawService {

    private final DrawRepositoryImpl drawRepository;
    private final ProductRepositoryImpl productRepository;

    /**
     * 응모 생성
     *
     * @param entrantId
     * @param productId
     * @return
     */
    public Draw createDraw(String entrantId, String productId) {

        // 사용자 인증 상태 확인(사용자 인증 개념 추가 후 적용 가능)

        // 사용자 중복 응모 여부 확인
        List<Draw> draws = drawRepository.findAllByEntrantId(entrantId);
        if(draws.size() > 1){
            throw new RuntimeException("Draw already exists");
        }

        // 상품 존재 여부 확인
        Product product = productRepository.findById(productId);
        if(product == null){
            throw new RuntimeException("Product not found");
        }

        // 상품 유효기간 여부 확인
        LocalDateTime now = LocalDateTime.now();
        if(!now.isAfter(product.getStartDt())){
            throw new RuntimeException("Draw is not open yet");
        } else if(!now.isBefore(product.getEndDt())){
            throw new RuntimeException("Draw is close");
        }

        // 응모 생성
        Draw draw = new Draw(entrantId, productId);
        return drawRepository.save(draw);
    }

    /**
     * 당첨자 발표
     *
     * @param productId
     * @return
     */
    public List<Draw> announceResult(String productId){

        // 상품 존재 여부 확인
        Product product = productRepository.findById(productId);
        if(product == null){
            throw new RuntimeException("Product not found");
        }

        // 상품 재고 확인
        if(product.getStock() < 1){
            throw new RuntimeException("Stock is less than 1");
        }

        // 상품 관련 응모 조회(List)
        List<Draw> draws = drawRepository.findDrawsByProductId(productId);

        // 응모자 존재 여부 확인
        if (draws.isEmpty()) {
            throw new RuntimeException("Draw not found");
        }

        // 상품 재고에 따른 랜덤하게 응모 선택
        List<Draw> shuffleDraws = selectWinner(draws, product.getStock());

        // 랜덤하게 선택된 응모 상태값 변경 (실패 -> 당첨)
        for(Draw draw : shuffleDraws){
            drawRepository.updateIsWinnerById(draw.getId());
        }

        // 비동기로 당첨자에게 알림(이메일, 푸시 알림 등)

        return shuffleDraws;
    }

    /**
     * 상품 재고만큼 응모 추출
     *
     * @param draws
     * @param stock
     * @return
     */
    public List<Draw> selectWinner(List<Draw> draws, long stock) {

        if(stock > draws.size()){
            // 재고가 응모수 보다 많은 경우를 체크해야 할까?
        }

        List<Draw> shuffleDraws = new ArrayList<>(draws);
        Collections.shuffle(shuffleDraws);

        return shuffleDraws.subList(0, Long.valueOf(stock).intValue());
    }

}
