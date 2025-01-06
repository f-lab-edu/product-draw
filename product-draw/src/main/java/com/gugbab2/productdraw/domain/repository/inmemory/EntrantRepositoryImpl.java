package com.gugbab2.productdraw.domain.repository.inmemory;

import com.gugbab2.productdraw.domain.entity.Entrant;
import com.gugbab2.productdraw.domain.repository.EntrantRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class EntrantRepositoryImpl implements EntrantRepository {
    private final Map<String, Entrant> entrantDatabase = new HashMap<>();

    @Override
    public Entrant findById(String id) {
        return entrantDatabase.get(id);
    }
}
