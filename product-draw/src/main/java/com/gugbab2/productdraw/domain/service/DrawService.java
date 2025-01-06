package com.gugbab2.productdraw.domain.service;

import com.gugbab2.productdraw.domain.entity.Draw;
import com.gugbab2.productdraw.domain.entity.Entrant;
import com.gugbab2.productdraw.domain.entity.Product;
import com.gugbab2.productdraw.domain.repository.inmemory.DrawRepositoryImpl;
import com.gugbab2.productdraw.domain.repository.inmemory.EntrantRepositoryImpl;
import com.gugbab2.productdraw.domain.repository.inmemory.ProductRepositoryImpl;
import com.gugbab2.productdraw.exception.DrawNotFoundException;
import com.gugbab2.productdraw.exception.EntrantNotFoundException;
import com.gugbab2.productdraw.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DrawService {

    private final DrawRepositoryImpl drawRepository;
    private final EntrantRepositoryImpl entrantRepository;
    private final ProductRepositoryImpl productRepository;

    /**
     * 응모 생성
     *
     * @param entrantId
     * @param productId
     * @return
     */
    public Draw createDraw(String entrantId, String productId) {

        Entrant entrant = entrantRepository.findById(entrantId);
        // 사용자 중복 요청 예외처리
        if(entrant == null) {
            throw new EntrantNotFoundException(entrantId);
        }

        Product product = productRepository.findById(productId);
        // 상품 미조회 예외처리
        if(product == null) {
            throw new ProductNotFoundException(productId);
        }

        // 응모 생성
        Draw draw = new Draw(entrantId, productId);
        return drawRepository.save(draw);
    }

    /**
     * 응모 당첨자 생성
     *
     * @param productId
     * @return
     */
    public List<Entrant> announceResult(String productId) {

        Product product = productRepository.findById(productId);
        // 상품 미조회 예외처리
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }

        // 상품에 대한 모든 응모자 조회
        List<Draw> draws = drawRepository.findByProductId(productId);
        if (draws == null || draws.isEmpty()) {
            throw new DrawNotFoundException(productId);
        }

        // 응모자 목록에서 Entrant 객체 추출
        List<Entrant> entrants = new ArrayList<>();
        for (Draw draw : draws) {
            Entrant entrant = entrantRepository.findById(draw.getEntrantId());
            if (entrant != null) {
                entrants.add(entrant);
            }
        }

        // 응모자 중 재고 만큼 당첨자를 선정해
        int stockCount = product.getStock();
        int winnerCount = Math.min(entrants.size(), stockCount);
        List<Entrant> winners = pickRandomWinners(entrants, winnerCount);

        // 당첨 결과를 업데이트 해줘
        for (Entrant winner : winners) {
            List<Draw> winnerDraws = drawRepository.findByEntrantId(winner.getId());
            for(Draw draw : winnerDraws) {
                drawRepository.updateById(draw);
            }
        }

        return winners;
    }

    /**
     * 응모자 중 당첨자 생성
     *
     * @param entrants
     * @param winnerCount
     * @return
     */
    private List<Entrant> pickRandomWinners(List<Entrant> entrants, int winnerCount) {
        List<Entrant> winners = new ArrayList<>();
        Random random = new Random();
        Set<Integer> selectedIndices = new HashSet<>();

        while (selectedIndices.size() < winnerCount) {
            int index = random.nextInt(entrants.size());
            if (!selectedIndices.contains(index)) {
                selectedIndices.add(index);
                winners.add(entrants.get(index));
            }
        }
        return winners;
    }

}
