package com.example.apputils.redis.spring.impl;

import com.example.apputils.redis.api.IRedisService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/11/27 16:43
 */
public class JedisRedisServiceImpl implements IRedisService {
    @Override
    public long decr(String key) {
        return 0;
    }

    @Override
    public void put(String key, Object value) {

    }

    @Override
    public void put(String key, Object value, int TTL) {

    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public Object remove(String key) {
        return null;
    }

    @Override
    public void removeByKeyLike(String keyPattern) {

    }

    @Override
    public Set<String> getKeys(String keyPattern) {
        return null;
    }

    @Override
    public boolean containsKey(String key) {
        return false;
    }

    @Override
    public void hput(String key, String field, Object value) {

    }

    @Override
    public void hmput(String key, Map value) {

    }

    @Override
    public List<Object> hmget(String key, Object[] fields) {
        return null;
    }

    @Override
    public long expire(String key, int TTL) {
        return 0;
    }

    @Override
    public Object hget(String key, String field) {
        return null;
    }

    @Override
    public Map hgetAll(String key) {
        return null;
    }

    @Override
    public long hdel(String key, String fields) {
        return 0;
    }

    @Override
    public boolean hdelall(String key) {
        return false;
    }

    @Override
    public Boolean hexists(String key, String field) {
        return null;
    }

    @Override
    public long lpush(String key, Object obj) {
        return 0;
    }

    @Override
    public Object rpop(String key) {
        return null;
    }

    @Override
    public Object lpop(String key) {
        return null;
    }

    @Override
    public List getList(String key) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getListByKeyLike(String pattern) {
        return null;
    }

    @Override
    public long sadd(String key, Object members) {
        return 0;
    }

    @Override
    public long srem(String key, Object members) {
        return 0;
    }

    @Override
    public boolean smove(String srckey, String dstkey, Object member) {
        return false;
    }

    @Override
    public long incr(String key) {
        return 0;
    }

    @Override
    public long hincr(String key, String field, long addBy) {
        return 0;
    }

    @Override
    public long batchHPut(Map<String, Map> objmaps) {
        return 0;
    }

    @Override
    public long batchDel(List<String> bigkeyList) {
        return 0;
    }

    @Override
    public List<Map> batchGet(List<String> keyObjectList) {
        return null;
    }

    @Override
    public Set<String> getKeysByPattern(String pattern) {
        return null;
    }

    @Override
    public Boolean zadd(String key, double score, Object member) {
        return null;
    }

    @Override
    public Long zadd(String key, Map<Object, Double> members) {
        return null;
    }

    @Override
    public Long zcard(String key) {
        return null;
    }

    @Override
    public Long zcount(String key, Double min, Double max) {
        return null;
    }

    @Override
    public Set<Object> zrange(String key, long start, long end) {
        return null;
    }

    @Override
    public Set<Object> zrangeByScore(String key, double start, double end) {
        return null;
    }

    @Override
    public Set<Object> zrangeByScore(String key, double min, double max, int offset, int count) {
        return null;
    }

    @Override
    public Long zrem(String key, Object[] members) {
        return null;
    }

    @Override
    public Long zremrangeByRank(String key, long start, long end) {
        return null;
    }

    @Override
    public Long zremrangeByScore(String key, Double start, Double end) {
        return null;
    }

    @Override
    public Long getExpire(String key) {
        return null;
    }

    @Override
    public String getID() {
        return null;
    }

    @Override
    public String getID(String type) {
        return null;
    }

    @Override
    public Map<String, Object> getMapByKeyLike(String pattern) {
        return null;
    }

    @Override
    public String encryptGet(String key) throws IOException {
        return null;
    }

    @Override
    public void encryptPut(String key, String value) throws IOException {

    }

    @Override
    public void encryptPut(String key, String value, long ttl) throws IOException {

    }

    @Override
    public Map<String, String> encryptHgetAll(String key) throws IOException {
        return null;
    }

    @Override
    public void encryptHmput(String key, Map<String, String> map) throws IOException {

    }
}
