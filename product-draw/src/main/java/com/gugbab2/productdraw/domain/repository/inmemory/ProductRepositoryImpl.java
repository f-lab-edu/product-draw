package com.gugbab2.productdraw.domain.repository.inmemory;

import com.gugbab2.productdraw.domain.entity.Product;
import com.gugbab2.productdraw.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final Map<String, Product> productDatabase = new HashMap<>();

    @Override
    public Product findById(String id) {
        return productDatabase.get(id);
    }
}
