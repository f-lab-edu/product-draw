package com.gugbab2.productdraw.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productId) {
        super("Product not found with ID : " + productId);
    }
}
