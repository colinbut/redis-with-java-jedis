/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.redis.jedis.datastructures;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class RedisSortedSets {

    public static void main(String[] args){
        Jedis jedis = new Jedis();

        Map<String, Integer> scores = new HashMap<String, Integer>();
        scores.put("player1", 56);
        scores.put("player2", 156);
        scores.put("player3", 90);

        for (String player : scores.keySet()) {
            jedis.zadd("ranking", scores.get(player), player);
        }

        String topPlayer = jedis.zrevrange("ranking", 0, 1).iterator().next();
        System.out.println(topPlayer); // should be player2

        // get the ranking of a particular player
        Long rank = jedis.zrevrank("ranking", "player3");
        System.out.println("The rank of player3 is : " + ++rank); // rank is 0 based!

        jedis.close();
    }
}
