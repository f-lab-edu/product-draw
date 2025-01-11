package com.gugbab2.productdraw.domain.repository;

import com.gugbab2.productdraw.domain.entity.Product;

public interface ProductRepository {
    Product findById(String id);
}
