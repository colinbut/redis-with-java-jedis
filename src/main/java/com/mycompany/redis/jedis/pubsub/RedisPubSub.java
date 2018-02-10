/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.redis.jedis.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisPubSub {

    public static void main(String[] args) {

        String channel = "channel";

        Jedis jedisSubscriber = new Jedis();

        // this is a blocking method apparently hmmm...
        jedisSubscriber.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                super.onMessage(channel, message);
                System.out.println("Recieved message: " + message + " on channel: " + channel);
            }
        }, channel);




        jedisSubscriber.close();
    }
}
