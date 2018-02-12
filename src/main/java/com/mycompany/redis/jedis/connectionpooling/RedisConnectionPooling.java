/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.redis.jedis.connectionpooling;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.Set;

public class RedisConnectionPooling {


    private static JedisPool getJedisPool() {
        //building config
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(128);
        jedisPoolConfig.setMaxIdle(128);
        jedisPoolConfig.setMinIdle(16);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        jedisPoolConfig.setNumTestsPerEvictionRun(3);
        jedisPoolConfig.setBlockWhenExhausted(true);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "localhost");

        return jedisPool;
    }

    public static void main(String[] args) {
        try(Jedis jedis = getJedisPool().getResource()){
            jedis.sadd("nicknames", "Colly");
            jedis.sadd("nicknames", "YoYo");

            Set<String> nickNames = jedis.smembers("nicknames");
            for (String nickname : nickNames) {
                System.out.println(nickname);
            }

        }
    }
}
