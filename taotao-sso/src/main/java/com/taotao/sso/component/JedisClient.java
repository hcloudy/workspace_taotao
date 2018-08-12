package com.taotao.sso.component;

public interface JedisClient {

    public String set(String key, String value);
    public String get(String key);
    public Long hset(String key, String item, String value);
    public String hget(String key, String item);
    public Long expire(String item, int second);
    public Long ttl(String item);
    public Long hdel(String key, String item);

}
