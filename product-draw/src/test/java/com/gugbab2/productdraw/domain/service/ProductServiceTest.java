package com.gugbab2.productdraw.domain.service;

import com.gugbab2.productdraw.domain.entity.Product;
import com.gugbab2.productdraw.domain.repository.inmemory.ProductRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    private ProductRepositoryImpl productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("제품 생성")
    void testCreateProduct() {

        Product mockProduct = new Product("product-123", "Test Product", 100.0, "L", "Test Category");

        when(productRepository.save(any(Product.class))).thenReturn(mockProduct);

        Product createdProduct = productService.createProduct(mockProduct);

        // Assert
        assertNotNull(createdProduct.getId());
        assertEquals("Test Product", createdProduct.getName());
        assertEquals("Test Category", createdProduct.getCategory());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("하나의 제품 조회")
    void testGetProduct() {

        String productId = "product-123";
        Product mockProduct = new Product("product-123", "Test Product", 100.0, "L", "Test Category");

        when(productRepository.findById(productId)).thenReturn(mockProduct);

        Product foundProduct = productService.getProduct(productId);

        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getId());
        assertEquals("Test Product", foundProduct.getName());
        assertEquals("Test Category", foundProduct.getCategory());

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    @DisplayName("카테고리를 조건으로 다수의 제품 조회")
    void testGetProductsByCategory() {

        int page = 0;
        int size = 3;

        String category = "Test Category";
        Product mockProduct1 = new Product("product-111", "Test Product 1", 100.0, "L", category);
        Product mockProduct2 = new Product("product-222", "Test Product 2", 100.0, "L", category);
        Product mockProduct3 = new Product("product-333", "Test Product 3", 100.0, "L", category);

        when(productRepository.findByCategory(category)).thenReturn(List.of(mockProduct1, mockProduct2, mockProduct3));

        List<Product> products = productService.getProductsByCategory(category, page, size);

        assertNotNull(products);
        assertEquals(3, products.size());
        assertEquals("Test Product 1", products.get(0).getName());
        assertEquals("Test Product 2", products.get(1).getName());
        assertEquals("Test Product 3", products.get(2).getName());
        verify(productRepository, times(1)).findByCategory(category);
    }
}