package com.gugbab2.productdraw.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Product {
    private String id;
    private String name;
    private String description;
    private long price;
    private long stock;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
}
