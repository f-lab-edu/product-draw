package com.gugbab2.productdraw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class DrawDto {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CreateDrawDto{
        private String entrantId;
        private String productId;
    }

}
