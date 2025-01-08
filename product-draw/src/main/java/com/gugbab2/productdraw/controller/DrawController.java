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
        Draw entry = drawService.createDraw(drawDto.getEntrantId(), drawDto.getProductId());
        return new ResponseEntity<>(entry, HttpStatus.CREATED);
    }

    // 응모 결과 발표(어드민 서버)
    @PostMapping("/{productId}/result")
    public ResponseEntity<List<String>> announceWinners(@PathVariable String productId) {
        List<String> entrants = drawService.announceResult(productId);
        return new ResponseEntity<>(entrants, HttpStatus.OK);
    }
}
