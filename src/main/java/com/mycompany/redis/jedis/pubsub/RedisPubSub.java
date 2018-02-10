/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.redis.jedis.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisPubSub {

    static final String CHANNEL = "channel";

    static final int PROGRESS_TIME_IN_MS = 50000;

    static final JedisPubSub jedisPubSub = new SubscriberHandler().getJedisPubSub();
    static PublisherProducer publisherProducer = new PublisherProducer();
    static SubscriberConsumer subscriberConsumer = new SubscriberConsumer();

    private static boolean terminated = false;

    private static void setTerminated() {
        terminated = true;
    }

    private static boolean isTerminated(){
        return terminated;
    }

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

    private static class SubscriberConsumer {
        final Jedis jedisSubscriber = new Jedis();

        void consumeMessage(JedisPubSub jedisPubSub) {
            jedisSubscriber.subscribe(jedisPubSub, CHANNEL);
        }

        void closeSubscriberJedis() {
            jedisSubscriber.close();
        }
    }

    private static class PublisherProducer {
        Jedis jedisPublisher = new Jedis();

        void produceMessage() {
            String message = "message";
            System.out.println("Publishing message: " + message);
            jedisPublisher.publish(CHANNEL, message);
        }

        void closePublisherJedis() {
            jedisPublisher.close();
        }
    }

    private static void cleanUpResourcesOnShutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (jedisPubSub.isSubscribed()) {
                jedisPubSub.unsubscribe();
            }
            subscriberConsumer.closeSubscriberJedis();
            publisherProducer.closePublisherJedis();
        }));
    }

    public static void main(String[] args) throws InterruptedException {

        Thread subscriberThread = new Thread(() -> {
            try {
                while (!isTerminated()) {
                    // this is a blocking method apparently hmmm...
                    subscriberConsumer.consumeMessage(jedisPubSub);

                    if (Thread.interrupted()){
                        throw new InterruptedException();
                    }
                }
            } catch (InterruptedException ex) {
                setTerminated();
            }
        });
        subscriberThread.start();

        Thread producerThread = new Thread(() -> {
            try {
                while (!isTerminated()) {
                    publisherProducer.produceMessage();

                    if (Thread.interrupted()){
                        throw new InterruptedException();
                    }
                }
            } catch (InterruptedException ex) {
                setTerminated();
            }
        });
        producerThread.start();

        Thread.sleep(PROGRESS_TIME_IN_MS);

        producerThread.interrupt();
        subscriberThread.interrupt();

        cleanUpResourcesOnShutdown();
    }
}
