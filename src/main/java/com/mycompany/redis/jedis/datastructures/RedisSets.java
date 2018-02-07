/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.redis.jedis.datastructures;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class RedisSets {

    public static void main(String[] args) {
        Jedis jedis = new Jedis();

        jedis.sadd("nicknames", "Jimmy");
        jedis.sadd("nicknames", "Jimmy"); // this will not be added (set doesn't allow duplicates)
        jedis.sadd("nicknames", "Bobby");

        Set<String> nickNames = jedis.smembers("nicknames");
        for (String nickname : nickNames) {
            System.out.println(nickname);
        }

        System.out.println(jedis.sismember("nicknames", "Jimmy"));

        jedis.close();
    }
}
