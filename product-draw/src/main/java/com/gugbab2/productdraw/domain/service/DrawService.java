package com.gugbab2.productdraw.domain.service;

import com.gugbab2.productdraw.domain.entity.Draw;
import com.gugbab2.productdraw.domain.repository.inmemory.DrawRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DrawService {

    private final DrawRepositoryImpl drawRepository;

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

        // 상품 존재 여부 확인

        // 상품 유효기간 여부 확인

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
    public List<String> announceResult(String productId){

        // 상품 존재 여부 확인

        // 상품 관련 응모 조회(List)
        List<Draw> draws = drawRepository.findDrawsByProductId(productId);

        // 응모자 존재 여부 확인
        if (draws.isEmpty()) {
            throw new RuntimeException("Draw not found");
        }

        // 상품 재고에 따른 랜덤하게 응모 선택
        List<String> entrants = new ArrayList<>();
        // 랜덤하게 선택된 응모 상태값 변경 (실패 -> 당첨)

        // 당첨자 목록 반환 후, 비동기로 당첨자에게 알림(이메일, 푸시 알림 등)
        return entrants;
    }

}
