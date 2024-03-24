package com.example.currency.component;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor
@Component
public class Cache<T, ID> {
    private static final int CACHE_SIZE = 1000;
    private final Map<ID, T> map = new LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<ID, T> eldest) {
            return size() > CACHE_SIZE;
        }
    };

    public Optional<T> getCachedById(ID id) {
        return Optional.ofNullable(map.get(id));
    }

    public void deleteCachedById(ID id) {
        map.remove(id);
    }

    public <S extends T> S saveCached(ID id, S entity) {
        return (S) map.put(id, entity);
    }
}
