package com.gugbab2.productdraw.domain.repository;

import com.gugbab2.productdraw.domain.entity.Entrant;

public interface EntrantRepository {

    Entrant findById(String id);
}
