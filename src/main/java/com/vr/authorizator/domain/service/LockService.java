package com.vr.authorizator.domain.service;

import com.vr.authorizator.domain.exception.LockAcquireException;
import com.vr.authorizator.domain.exception.LockReleasedException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.api.annotation.RInject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LockService<T> {

    @Autowired
    private RedissonClient redissonClient;

    @Value("${app.lock.default.ttl}")
    private Long defaultTTL;

    @Value("${app.lock.default.wait-time}")
    private Long defaultWaitTime;

    public T lock(String key, ThrowingSupplier<T> supplier) throws Exception {

        RLock lock = redissonClient.getLock(key);
        if(!lock.tryLock(defaultWaitTime, defaultTTL, TimeUnit.MILLISECONDS)) throw new LockAcquireException();

        try {
            return supplier.get();
        }finally {
            if(!lock.isLocked()) throw new LockReleasedException();
            lock.unlock();
        }
    }

    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws Exception;
    }
}
