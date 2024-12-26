package com.gugbab2.productdraw.domain.repository;

import com.gugbab2.productdraw.domain.entity.Product;

import java.util.List;

public interface ProductRepository {
    Product save(Product product);
    Product findById(String id);
    List<Product> findByCategory(String category);
}
