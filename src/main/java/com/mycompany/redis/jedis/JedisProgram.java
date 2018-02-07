/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.redis.jedis;

import redis.clients.jedis.Jedis;

public class JedisProgram {

    public static void main(String[] args){
        Jedis jedis = new Jedis();

        // Simple Strings - K/V pair

        /*
         * Using it as a 'cache'
         */

        //storing it
        jedis.set("events/city/paris", "32,15,233,328");

        //getting it back
        String value = jedis.get("events/city/paris");

        // should be 32,15,233,328
        System.out.println(value);

        // List
        jedis.lpush("queue#tasks", "first task");
        jedis.lpush("queue#tasks", "second task");
        jedis.lpush("queue#tasks", "third task");

        // pop the first task entered
        String firstTask = jedis.rpop("queue#tasks");

        jedis.close();
    }
}
