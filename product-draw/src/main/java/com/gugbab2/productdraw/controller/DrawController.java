package com.gugbab2.productdraw.controller;

import com.gugbab2.productdraw.domain.entity.Draw;
import com.gugbab2.productdraw.domain.entity.Entrant;
import com.gugbab2.productdraw.domain.entity.Payment;
import com.gugbab2.productdraw.domain.service.DrawService;
import com.gugbab2.productdraw.dto.DrawDto;
import com.gugbab2.productdraw.dto.PaymentDto;
import com.gugbab2.productdraw.exception.EntrantNotFoundException;
import com.gugbab2.productdraw.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/draws")
@RequiredArgsConstructor
public class DrawController {

    private final DrawService drawService;

    // 응모 제출
    @PostMapping("/submit")
    public ResponseEntity<?> createDraw(@RequestBody DrawDto.CreateDrawDto drawDto) {
        try {
            Draw entry = drawService.createDraw(drawDto.getEntrantId(), drawDto.getProductId());
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (EntrantNotFoundException | ProductNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 응모 결과 발표
    @PostMapping("/{productId}result")
    public ResponseEntity<List<Entrant>> announceWinners(@PathVariable String productId) {
        List<Entrant> entrants = drawService.announceResult(productId);
        return new ResponseEntity<>(entrants, HttpStatus.OK);
    }
}
