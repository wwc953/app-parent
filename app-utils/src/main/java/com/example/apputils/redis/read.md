# 2.0 通用组件
##kafka_2.11
    2.4.1

##CaffeineCache 2.6.2

##Redisson
    分布式锁
    3.10.7

###Redis序列化
    JedisRedis ObjectMapper.writeValueAsBytes(obj) --> byte[]
    
    public List<Object> hmget(String key,Object[] fields){
        byte[] rawkey = key.getByte("UTF-8");
        List<byte[]>  result = jedisCluster.hmget(byte[] rawkey,byte[] fields);
        return List<Object>;
        }
    
    ObjectMapper om = new ObjectMapper();
    private Object deserializeValue(byte[] bytes){
        return om.readValue(bytes,Object.class);
    }
    
### redis pipelined
    RedisTemplate、Jedis 支持
    this.template.executePipelined(new RedisCallback<Object>(){
        //xxxx
    });
    
    JedisCluster 需要自己根据key找到Jedis对象    
    
