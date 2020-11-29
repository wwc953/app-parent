package com.example.apputils.redis.jedis.impl;

import com.example.apputils.redis.api.IRedisService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/11/29 15:03
 */
public class JedisClusterRedisServiceImpl implements IRedisService {
    @Autowired
    JedisCluster jedisCluster;

    private ObjectMapper om = new ObjectMapper();

    @PostConstruct
    public void init() {
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    @Override
    public long decr(String key) {
        return jedisCluster.decr(key);
    }

    @Override
    public void put(String key, Object value) {
        jedisCluster.set(rawKey(key), rawValue(value));
    }

    @Override
    public void put(String key, Object value, int TTL) {
        jedisCluster.setex(rawKey(key), TTL, rawValue(value));
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
        return sacn(keyPattern);
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
        Map<byte[], byte[]> hashes = new LinkedHashMap<>(value.size());
        value.forEach((k, v) -> {
            hashes.put(rawKey(key), rawValue(v));
        });
        jedisCluster.hmset(rawKey(key), hashes);
    }

    @Override
    public List<Object> hmget(String key, Object[] fields) {
        byte[][] byteHashKeys = new byte[fields.length][];
        for (int i = 0; i < fields.length; i++) {
            byteHashKeys[i] = rawValue(fields[i]);
        }
        List<byte[]> hmget = jedisCluster.hmget(rawKey(key), byteHashKeys);
        return deserializeHashValue(hmget);
    }

    @Override
    public long expire(String key, int TTL) {
        return 0;
    }

    @Override
    public Object hget(String key, String field) {
        return jedisCluster.hget(rawKey(key), rawValue(field));
    }

    @Override
    public Map hgetAll(String key) {
        return deserializeHashMap(jedisCluster.hgetAll(rawKey(key)));
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
        return jedisCluster.lpush(rawKey(key), new byte[][]{rawValue(obj)});
    }

    @Override
    public Object rpop(String key) {
        return jedisCluster.rpop(rawKey(key));
    }

    @Override
    public Object lpop(String key) {
        return null;
    }

    @Override
    public List getList(String key) {
        byte[] bytesKey = rawKey(key);
        Long size = jedisCluster.llen(bytesKey);
        return deserializeHashValue(jedisCluster.lrange(bytesKey, 0L, size));
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
        objmaps.forEach((k, v) -> {
            this.hmput(k, v);
        });
        return objmaps.size();
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
        return sacn(pattern);
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
        return jedisCluster.ttl(rawKey(key));
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


    private byte[] rawKey(String key) {
        try {
            return key.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] rawValue(Object value) {
        try {
            return value instanceof byte[] ? (byte[]) value : om.writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("序列化失败！！！");
        }
    }

    private Set<String> sacn(String pattern) {
        String cursor = ScanParams.SCAN_POINTER_START;
        ScanParams scanParams = new ScanParams();
        scanParams.count(1000);
        HashSet<String> result = new HashSet<>();
        do {
            ScanResult<String> scanResult = jedisCluster.scan(cursor, scanParams);
            cursor = scanResult.getCursor();
//            cursor = scanResult.getStringCursor(); //2.9版本 2.0使用的版本，可能需要修改
            result.addAll(scanResult.getResult());
        } while (!"0".equals(cursor));

        return result;
    }

    private List<Object> deserializeHashValue(List<byte[]> bytes) {
        return bytes.stream().map(t -> deserializeValue(t)).collect(Collectors.toList());
    }

    private Object deserializeValue(byte[] bytes) {
        try {
            return om.readValue(bytes, Object.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <HK, HV> Map<HK, HV> deserializeHashMap(Map<byte[], byte[]> entries) {
        Map map = new LinkedHashMap<>(entries.size());
        Iterator iterator = entries.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<byte[], byte[]> entry = (Map.Entry<byte[], byte[]>) iterator.next();
            map.put(deserializeValue(entry.getKey()), deserializeValue(entry.getValue()));
        }
        return map;
    }

    public boolean isMember(Object key, Object member) {
        return jedisCluster.sismember(rawValue(key), rawValue(member));
    }
}
