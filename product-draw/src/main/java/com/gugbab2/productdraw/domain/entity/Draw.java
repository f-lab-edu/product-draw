package com.gugbab2.productdraw.domain.entity;

import com.gugbab2.productdraw.domain.vo.WinnerStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Draw {
    private String id;
    private String entrantId;
    private String productId;
    private boolean isPaid;
    private WinnerStatus winnerStatus;

    public Draw(String entrantId, String productId) {
        this.id = UUID.randomUUID().toString();
        this.entrantId = entrantId;
        this.productId = productId;
        this.isPaid = false;
        this.winnerStatus = WinnerStatus.PENDING;   // PENDING : 대기
    }
}
