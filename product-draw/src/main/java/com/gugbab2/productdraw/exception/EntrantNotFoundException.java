package com.gugbab2.productdraw.exception;

public class EntrantNotFoundException extends RuntimeException {
    public EntrantNotFoundException(String entrantId) {
        super("Entrant not found with ID : " + entrantId);
    }
}
