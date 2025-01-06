package com.gugbab2.productdraw.dto;

import lombok.Getter;
import lombok.Setter;

public class DrawDto {

    @Getter
    @Setter
    public static class CreateDrawDto{
        private String entrantId;
        private String productId;
    }

}
