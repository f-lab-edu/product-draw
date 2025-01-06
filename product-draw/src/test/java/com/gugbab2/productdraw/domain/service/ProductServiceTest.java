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

        /*
        [given]
        1. 고정된 mockProduct 리턴
         */
        Product mockProduct = new Product("product-123", "Test Product", 100.0, "L", "Test Category");

        when(productRepository.save(any(Product.class))).thenReturn(mockProduct);

        /*
        [when]
        1. 상품 등록 실행
         */
        Product createdProduct = productService.createProduct(mockProduct);

        /*
        [then]
        1. 생성된 상품의 id 는 not null 이어야 한다.
        2. 생성된 상품의 이름은 mock 객체 이름과 동일해야 한다.
        3. 생성된 상품의 카테고리는 mock 객체 카테고리와 동일해야 한다.
         */
        assertNotNull(createdProduct.getId());
        assertEquals("Test Product", createdProduct.getName());
        assertEquals("Test Category", createdProduct.getCategory());

//        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("하나의 제품 조회")
    void testGetProduct() {

        String productId = "product-123";
        /*
        [given]
        1. 고정된 mockProduct 리턴
         */
        Product mockProduct = new Product("product-123", "Test Product", 100.0, "L", "Test Category");

        when(productRepository.findById(productId)).thenReturn(mockProduct);

        /*
        [when]
        1. 상품 조회(단건)
         */
        Product foundProduct = productService.getProduct(productId);

        /*
        [when]
        1. 조회한 상품은 not null 이어야 한다.
        2. 조회한 상품의 ID 는 mock 객체의 ID 이어야 한다.
        3. 조회한 상품의 이름은 mock 객체의 이름이어야 한다.
        4. 조회한 상품의 카테고리는 mock 객체의 카테고리어야 한다.
         */
        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getId());
        assertEquals("Test Product", foundProduct.getName());
        assertEquals("Test Category", foundProduct.getCategory());

//        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    @DisplayName("카테고리를 조건으로 다수의 제품 조회")
    void testGetProductsByCategory() {

        int page = 0;
        int size = 3;

        String category = "Test Category";

        /*
        [given]
        1. 동일한 카테고리의 products 리턴
         */
        Product mockProduct1 = new Product("product-111", "Test Product 1", 100.0, "L", category);
        Product mockProduct2 = new Product("product-222", "Test Product 2", 100.0, "L", category);
        Product mockProduct3 = new Product("product-333", "Test Product 3", 100.0, "L", category);

        when(productRepository.findByCategory(category)).thenReturn(List.of(mockProduct1, mockProduct2, mockProduct3));

        /*
        [when]
        1. 카테고리 기반으로 상품 조회
         */
        List<Product> products = productService.getProductsByCategory(category, page, size);

        /*
        [when]
        1. 상품이 not null 이면 안된다.
        2. 상품이 이름이 순서대로 mock 상품의 이름과 동일해야 한다.
         */
        assertNotNull(products);
        assertEquals("Test Product 1", products.get(0).getName());
        assertEquals("Test Product 2", products.get(1).getName());
        assertEquals("Test Product 3", products.get(2).getName());

//        verify(productRepository, times(1)).findByCategory(category);
    }
}