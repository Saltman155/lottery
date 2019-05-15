package com.superywd.lottery.main.model.config;

import com.superywd.library.properties.Property;

/**
 * @author 迷宫的中心
 * @date 2019/5/15 23:21
 */
public class RedisConfig {

    @Property(key = "redis.host",defaultValue = "127.0.0.1")
    public static String host;
    @Property(key = "redis.port",defaultValue = "6379")
    public static String port;
    @Property(key = "redis.password",defaultValue = "")
    public static String password;
    @Property(key = "redis.dbIndex",defaultValue = "0")
    public static String dbIndex;
    @Property(key = "redis.expiration",defaultValue = "3000")
    public static long expiration;
    @Property(key = "redis.maxIdle",defaultValue = "300")
    public static int maxIdle;
    @Property(key = "redis.maxActive",defaultValue = "600")
    public static int maxWait;

}
