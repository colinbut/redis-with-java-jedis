/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.redis.jedis.datastructures;

import redis.clients.jedis.Jedis;

import java.util.Map;

public class RedisHashses {

    public static void main(String[] args) {
        Jedis jedis = new Jedis();

        String hashMap = "#user";

        jedis.hset(hashMap, "name", "Colin But");
        jedis.hset(hashMap, "email", "colin.but@email.com");

        String name = jedis.hget(hashMap, "name");
        System.out.println(name);

        Map<String, String> fields = jedis.hgetAll(hashMap);
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            System.out.println(entry.getKey() + "-" + entry.getValue());
        }

        System.out.print("Email is: " + fields.get("email"));

        jedis.close();
    }
}
