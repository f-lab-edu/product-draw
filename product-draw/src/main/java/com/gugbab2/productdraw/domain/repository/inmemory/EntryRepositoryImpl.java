package com.gugbab2.productdraw.domain.repository.inmemory;

import com.gugbab2.productdraw.domain.entity.Entry;
import com.gugbab2.productdraw.domain.repository.EntryRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class EntryRepositoryImpl implements EntryRepository {
    private final Map<String, Entry> entryDatabase = new HashMap<>();

    @Override
    public Entry save(Entry entry) {
        if (entry.getId() == null) {
            // 예외처리
        }
        entryDatabase.put(entry.getId(), entry);
        return entry;
    }

    @Override
    public Entry findById(String id) {
        return entryDatabase.get(id);
    }
}
