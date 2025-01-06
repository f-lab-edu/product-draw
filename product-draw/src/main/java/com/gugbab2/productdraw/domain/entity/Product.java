package com.gugbab2.productdraw.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Product {
    private String id;
    private String name;
    private double price;
    private String size;
    private String category;
    private boolean isDone;
    private int stock;

    public Product(String name, double price, String size, String category, int stock) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.size = size;
        this.category = category;
        this.isDone = false;
        this.stock = stock;
    }
}
