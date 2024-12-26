package com.gugbab2.productdraw.domain.repository;

import com.gugbab2.productdraw.domain.entity.Entry;

public interface EntryRepository {
    Entry save(Entry entry);
    Entry findById(String id);
}
