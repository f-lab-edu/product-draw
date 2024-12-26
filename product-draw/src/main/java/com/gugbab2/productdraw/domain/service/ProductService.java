package com.gugbab2.productdraw.domain.service;

import com.gugbab2.productdraw.domain.entity.Product;
import com.gugbab2.productdraw.domain.repository.inmemory.ProductRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepositoryImpl productRepository;

    public Product createProduct(Product product) {
        product.setId(UUID.randomUUID().toString());
        return productRepository.save(product);
    }

    public Product getProduct(String id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategory(String category, int page, int size) {
        List<Product> filteredProducts = productRepository.findByCategory(category);

        int start = page * size;
        int end = Math.min(start + size, filteredProducts.size());

        if (start > filteredProducts.size()) {
            return List.of();
        }

        return filteredProducts.subList(start, end);
    }
}
