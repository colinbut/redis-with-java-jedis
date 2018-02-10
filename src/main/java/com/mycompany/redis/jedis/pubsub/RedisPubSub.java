/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.redis.jedis.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisPubSub {

    public static final String CHANNEL = "channel";

    private static class SubscriberHandler {
        JedisPubSub getJedisPubSub() {
            return new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    super.onMessage(channel, message);
                    System.out.println("Recieved message: " + message + " on channel: " + channel);
                }
            };
        }
    }

    public static void main(String[] args) throws InterruptedException {

        final int terminateCondition = 1;
        final Jedis jedisSubscriber = new Jedis();
        final JedisPubSub jedisPubSub = new SubscriberHandler().getJedisPubSub();

        new Thread(new Runnable() {
            public void run() {
                while (terminateCondition != 999) {
                    // this is a blocking method apparently hmmm...
                    jedisSubscriber.subscribe(jedisPubSub, CHANNEL);
                }
            }
        }).start();

        Thread.sleep(10000);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if (jedisPubSub.isSubscribed()) {
                    jedisPubSub.unsubscribe();
                }
                jedisSubscriber.close();
            }
        }));

    }
}
