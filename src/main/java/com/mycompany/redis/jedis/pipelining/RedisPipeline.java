/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.redis.jedis.pipelining;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.Set;

public class RedisPipeline {

    public static void main(String[] args) {
        Jedis jedis = new Jedis();

        String userOneId = "4322523";
        String userTwoId = "4323524";

        Pipeline pipeline = jedis.pipelined();
        pipeline.sadd("searched#" + userOneId, "paris");
        pipeline.zadd("ranking", 126, userOneId);
        pipeline.zadd("ranking", 325, userTwoId);
        Response<Boolean> pipeExists = pipeline.sismember("searched#" + userOneId, "paris");
        Response<Set<String>> pipeRanking = pipeline.zrange("ranking", 0, -1);

        pipeline.sync();

        Boolean exists = pipeExists.get();
        Set<String> ranking = pipeRanking.get();

        System.out.println(exists);
        System.out.println(ranking);

        jedis.close();
    }
}
