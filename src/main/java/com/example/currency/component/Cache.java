package com.example.currency.component;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@NoArgsConstructor
@Component
public class Cache<T, I> {
    private static final int CACHE_SIZE = 1000;
    private final Map<I, T> map = new LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<I, T> eldest) {
            return size() > CACHE_SIZE;
        }
    };

    public Optional<T> getCachedById(I id) {
        Optional<T> result = Optional.ofNullable(map.get(id));
        if(result.isPresent()) {
            map.remove(id);
            map.put(id, result.get());
        }
        return result;
    }

    public void deleteCachedById(I id) {
        map.remove(id);
    }

    public <S extends T> S saveCached(I id, S entity) {
        return (S) map.put(id, entity);
    }
}
