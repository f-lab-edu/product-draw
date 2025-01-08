package com.gugbab2.productdraw.controller;

import com.gugbab2.productdraw.domain.entity.Draw;
import com.gugbab2.productdraw.domain.service.DrawService;
import com.gugbab2.productdraw.dto.DrawDto;
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

    // 응모 제출(프론트엔드 서버)
    @PostMapping("/submit")
    public ResponseEntity<?> createDraw(@RequestBody DrawDto.CreateDrawDto drawDto) {
        try {
            Draw entry = drawService.createDraw(drawDto.getEntrantId(), drawDto.getProductId());
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    // 응모 결과 발표(어드민 서버)
    @PostMapping("/{productId}/result")
    public ResponseEntity<?> announceWinners(@PathVariable String productId) {
        try {
            List<Draw> Draws = drawService.announceResult(productId);
            return new ResponseEntity<>(Draws, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
