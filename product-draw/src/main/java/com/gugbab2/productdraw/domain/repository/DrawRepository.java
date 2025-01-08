package com.gugbab2.productdraw.domain.repository;

import com.gugbab2.productdraw.domain.entity.Draw;

import java.util.List;

public interface DrawRepository {
    Draw save(Draw entry);
    Draw findById(String id);
    List<Draw> findDrawsByProductId(String productId);
}
