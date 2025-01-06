package com.gugbab2.productdraw.exception;

public class DrawNotFoundException extends RuntimeException {
    public DrawNotFoundException(String productId) {
        super("Draw not found with Product ID : " + productId);
    }
}
