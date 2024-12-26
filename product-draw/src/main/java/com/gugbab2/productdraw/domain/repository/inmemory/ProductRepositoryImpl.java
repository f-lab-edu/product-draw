package com.gugbab2.productdraw.domain.repository.inmemory;

import com.gugbab2.productdraw.domain.entity.Product;
import com.gugbab2.productdraw.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final Map<String, Product> productDatabase = new HashMap<>();

    @Override
    public Product save(Product product) {
        productDatabase.put(product.getId(), product);
        return product;
    }

    @Override
    public Product findById(String id) {
        return productDatabase.get(id);
    }

    @Override
    public List<Product> findByCategory(String category) {
        List<Product> result = new ArrayList<>();

        for (Product product : productDatabase.values()) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                result.add(product);
            }
        }

        return result;
    }

}
