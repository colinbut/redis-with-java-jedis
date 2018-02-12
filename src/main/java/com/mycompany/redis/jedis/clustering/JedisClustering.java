/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.redis.jedis.clustering;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;

public class JedisClustering {

    public static void main(String[] args) {
        try (JedisCluster jedisCluster  = new JedisCluster(new HostAndPort("localhost", 6379))) {

            // using JedisCluster as if normal Jedis resource

            // Transactions & Pipeline are not supported in clustering

        } catch (IOException e) {
            // ignore
        }
    }
}
