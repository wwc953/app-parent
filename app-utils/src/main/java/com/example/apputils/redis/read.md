# 2.0 通用组件
##kafka_2.11
    2.4.1

##CaffeineCache 2.6.2

##Redisson
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
