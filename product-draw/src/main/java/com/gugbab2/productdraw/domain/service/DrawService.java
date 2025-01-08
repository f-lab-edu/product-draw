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
//    public Draw createDraw(String entrantId, String productId) {
//
//        // 사용자 인증 상태 확인(응작 개념
//        Entrant entrant = entrantRepository.findById(entrantId);
//        // 사용자 중복 요청 예외처리
//        if(entrant == null) {
//            throw new EntrantNotFoundException(entrantId);
//        }
//
//        Product product = productRepository.findById(productId);
//        // 상품 미조회 예외처리
//        if(product == null) {
//            throw new ProductNotFoundException(productId);
//        }
//
//        // 응모 생성
//        Draw draw = new Draw(entrantId, productId);
//        return drawRepository.save(draw);
//    }
//
//    /**
//     * 당첨자 발표
//     *
//     * @param productId
//     * @return
//     */
//    public List<Entrant> announceResult(String productId){
//
//        Product product = productRepository.findById(productId);
//        // 상품 미조회 예외처리
//        if(product == null) {
//            throw new ProductNotFoundException(productId);
//        }
//        if(product.isDone()){
//           throw new ProductExpiredException(productId);
//        }
//
//
//
//    }

}
