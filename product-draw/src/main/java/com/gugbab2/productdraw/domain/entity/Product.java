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

    public Product(String id, String name, double price, String size, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = size;
        this.category = category;
    }
}
