package com.cjt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author caojiantao
 */
@Component
public class RedisLock {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * Redis原生连接，采用自动注入初始化
     */
    private final JedisConnectionFactory jedisConnectionFactory;

    @Autowired
    public RedisLock(JedisConnectionFactory jedisConnectionFactory) {
        this.jedisConnectionFactory = jedisConnectionFactory;
    }

    /**
     * 尝试加锁
     *
     * @param lockKey    加锁的KEY
     * @param requestId  加锁客户端唯一ID标识
     * @param expireTime 过期时间
     * @param timeUnit   时间单位
     * @return 是否加锁成功
     */
    public boolean tryLock(String lockKey, String requestId, long expireTime, TimeUnit timeUnit) {
        JedisConnection connection = jedisConnectionFactory.getConnection();
        Jedis jedis = connection.getNativeConnection();
        boolean result = LOCK_SUCCESS.equals(jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, timeUnit.toMillis(expireTime)));
        connection.close();
        return result;
    }

    /**
     * 释放锁
     *
     * @param lockKey   加锁的KEY
     * @param requestId 加锁客户端唯一ID标识
     * @return 是否释放成功
     */
    public boolean releaseLock(String lockKey, String requestId) {
        // Lua代码，一次性执行保证原子性，避免并发问题
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        JedisConnection connection = jedisConnectionFactory.getConnection();
        Jedis jedis = connection.getNativeConnection();
        boolean result = RELEASE_SUCCESS.equals(jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId)));
        connection.close();
        return result;
    }
}
