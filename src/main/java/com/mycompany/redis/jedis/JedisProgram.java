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
        jedis.close();
    }
}
