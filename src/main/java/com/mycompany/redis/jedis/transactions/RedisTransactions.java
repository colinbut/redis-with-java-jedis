/*
 * |-------------------------------------------------
 * | Copyright © 2018 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.redis.jedis.transactions;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class RedisTransactions {

    public static void main(String[] args) {
        Jedis jedis = new Jedis();

        String friendsPrefix = "friends#";
        String userOneId = "234568";
        String userTwoId = "145623";

        Transaction transaction = jedis.multi();
        transaction.sadd(friendsPrefix + userOneId, userTwoId);
        transaction.sadd(friendsPrefix + userTwoId, userOneId);
        transaction.exec();

        //transaction.watch("friends#deleted" + userOneId);


        jedis.close();
    }
}
