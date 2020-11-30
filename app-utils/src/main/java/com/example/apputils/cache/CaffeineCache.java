package com.example.apputils.cache;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/11/30 10:45
 */
@Component
public class CaffeineCache {

    @Autowired
    Cache<String, Object> cache;

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public void del(String key) {
        cache.invalidate(key);
    }

    public String getString(String key) {
        Object value = cache.getIfPresent(key);
        return value != null ? value.toString() : null;
    }

    public List<Object> getListByKey(String key) {
        Object value = cache.getIfPresent(key);
        return value != null ? (List) value : null;
    }

    public Set<Object> getSetByKey(String key) {
        Object value = cache.getIfPresent(key);
        return value != null ? (Set) value : null;
    }

    public Object hget(String key, String item) {
        Object value = cache.getIfPresent(key);
        return ((Map) value).get(item);
    }

    public void hset(String key, String item, Object value) {
        Map map = (Map) cache.getIfPresent(key);
        if (map != null && map.size() > 0) {
            map.put(item, value);
        } else {
            Map itemMap = new HashMap();
            itemMap.put(item, value);
            cache.put(key, itemMap);
        }
    }

    public void hset(String key, Map<String, Object> inputMap) {
        Map map = (Map) cache.getIfPresent(key);
        if (map != null && map.size() > 0) {
            map.putAll(inputMap);
        } else {
            cache.put(key, inputMap);
        }
    }

    public Map<String, Object> hmget(String key) {
        return (Map) cache.getIfPresent(key);
    }

    public void delAll() {
        cache.invalidateAll();
    }

}
