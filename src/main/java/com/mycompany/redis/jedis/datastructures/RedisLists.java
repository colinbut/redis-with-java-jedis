/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.redis.jedis.datastructures;

import redis.clients.jedis.Jedis;

public class RedisLists {

    public static void main(String[] args) {
        Jedis jedis = new Jedis();

        // List
        jedis.lpush("queue#tasks", "first task");
        jedis.lpush("queue#tasks", "second task");
        jedis.lpush("queue#tasks", "third task");

        // pop the first task entered
        String firstTask = jedis.rpop("queue#tasks");

        System.out.println(firstTask);

        jedis.close();
    }
}
