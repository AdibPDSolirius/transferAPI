package com.revolut.transfer.application;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Singleton;

@Singleton
class Locks {

    private final ConcurrentMap<BigInteger, BigInteger> locks = new ConcurrentHashMap<>();

    Object getLock(final BigInteger id) {
        locks.putIfAbsent(id, id);
        return locks.get(id);
    }

}
