package com.gugbab2.productdraw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gugbab2.productdraw.domain.entity.Draw;
import com.gugbab2.productdraw.domain.entity.Product;
import com.gugbab2.productdraw.domain.repository.inmemory.DrawRepositoryImpl;
import com.gugbab2.productdraw.domain.repository.inmemory.ProductRepositoryImpl;
import com.gugbab2.productdraw.dto.DrawDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class DrawControllerIntegrationTest {

//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    private DrawRepositoryImpl drawRepository;
//
//    @Autowired
//    private ProductRepositoryImpl productRepository;
//
//    private MockMvc mockMvc;
//
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//        objectMapper = new ObjectMapper();
//
//        // 테스트용 상품 데이터 추가
//        Product product = new Product("product123", "Test Product", "Test Product", 1000, 2, LocalDateTime.now().minusDays(1),
//                LocalDateTime.now().plusDays(1));
//        productRepository.save(product);
//    }
//
//    @Test
//    void testFlowOfAPIRequests() throws Exception {
//        // Step 1: 응모 제출 API 호출
//        DrawDto.CreateDrawDto drawDto = new DrawDto.CreateDrawDto("entrant123", "product123");
//        String drawRequestJson = objectMapper.writeValueAsString(drawDto);
//
//        mockMvc.perform(post("/draws/submit")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(drawRequestJson))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.entrantId").value("entrant123"))
//                .andExpect(jsonPath("$.productId").value("product123"));
//
//        // Step 2: 당첨자 발표 API 호출
//        mockMvc.perform(post("/draws/product123/result"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].entrantId").value("entrant123"))
//                .andExpect(jsonPath("$[0].productId").value("product123"));
//
//        // Step 3: 당첨 데이터 검증 (Repository 사용)
//        Draw winner = drawRepository.findDrawsByProductId("product123").get(0);
//        assertNotNull(winner);
//        assertEquals("entrant123", winner.getEntrantId());
//        assertEquals(true, winner.isWinner());
//    }
}
