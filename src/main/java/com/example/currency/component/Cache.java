package com.example.currency.component;

import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor
public class Cache<T, ID> {
    private static final int CACHE_SIZE = 1000;
    private final Map<ID, T> cache = new LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<ID, T> eldest) {
            return size() > CACHE_SIZE;
        }
    };

    public Optional<T> getCachedById(ID id) {
        return Optional.ofNullable(cache.get(id));
    }

    public void deleteCachedById(ID id) {
        cache.remove(id);
    }

    public <S extends T> S saveCached(ID id, S entity) {
        return (S) cache.put(id, entity);
    }
}
