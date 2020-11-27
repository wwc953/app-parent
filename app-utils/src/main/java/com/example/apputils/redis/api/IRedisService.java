package com.example.apputils.redis.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: TODO
 * @author: wangwc
 * @date: 2020/11/27 15:42
 */
public interface IRedisService {

    long decr(String key);

    void put(String key, Object value);

    void put(String key, Object value, int TTL);

    Object get(String key);

    Object remove(String key);

    void removeByKeyLike(String keyPattern);

    Set<String> getKeys(String keyPattern);

    boolean containsKey(String key);

    void hput(String key, String field, Object value);

    void hmput(String key, Map value);

    List<Object> hmget(String key, Object[] fields);

    long expire(String key, int TTL);

    Object hget(String key, String field);

    Map hgetAll(String key);

    long hdel(String key, String fields);

    boolean hdelall(String key);

    Boolean hexists(String key, String field);

    long lpush(String key, Object obj);

    Object rpop(String key);

    Object lpop(String key);

    List getList(String key);

    List<Map<String, Object>> getListByKeyLike(String pattern);

    long sadd(String key, Object members);

    long srem(String key, Object members);

    boolean smove(String srckey, String dstkey, Object member);

    long incr(String key);

    long hincr(String key, String field, long addBy);

    long batchHPut(Map<String, Map> objmaps);

    long batchDel(List<String> bigkeyList);

    List<Map> batchGet(List<String> keyObjectList);

    Set<String> getKeysByPattern(String pattern);

    Boolean zadd(String key, double score, Object member);

    Long zadd(String key, Map<Object, Double> members);

    Long zcard(String key);

    Long zcount(String key, Double min, Double max);

    Set<Object> zrange(String key, long start, long end);

    Set<Object> zrangeByScore(String key, double start, double end);

    Set<Object> zrangeByScore(String key, double min, double max, int offset, int count);

    Long zrem(String key, Object[] members);

    Long zremrangeByRank(String key, long start, long end);

    Long zremrangeByScore(String key, Double start, Double end);

    Long getExpire(String key);

    String getID();

    String getID(String type);

    Map<String, Object> getMapByKeyLike(String pattern);

    String encryptGet(String key) throws IOException;

    void encryptPut(String key, String value) throws IOException;

    void encryptPut(String key, String value, long ttl) throws IOException;

    Map<String, String> encryptHgetAll(String key) throws IOException;

    void encryptHmput(String key, Map<String, String> map) throws IOException;
}
