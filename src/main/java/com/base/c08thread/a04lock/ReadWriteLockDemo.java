package com.base.c08thread.a04lock;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author joe
 * @create 2021-01-21 11:19
 */
/*
 4.4.独占锁（写）、共享锁（读）、互斥锁
 */
public class ReadWriteLockDemo {
    public static class MyCache{
        private volatile HashMap<String, String> map = new HashMap<>();
        private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

        public void put(String key, String value) {
            rwLock.writeLock().lock();
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            try {
                map.put(key, value);
                System.out.println(Thread.currentThread().getName() + "\t写入完成");
            } finally {
                rwLock.writeLock().unlock();
            }
        }

        public void get(String s) {
            System.out.println(Thread.currentThread().getName() + "\ttry读锁");
            rwLock.readLock().lock();
            try {
                String s1 = map.get(s);
                System.out.println(Thread.currentThread().getName() + "\t读取完成：" + s1);
            } finally {
                rwLock.readLock().unlock();
            }
        }
    }
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        // 先 注册3个写线程
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.put(finalI + "", finalI + ""); // 写
            }, String.valueOf(i)).start();
        }

        // 再 注册3个读线程
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.get(finalI + ""); // 读
            }, String.valueOf(i)).start();
        }

        // 然后 注册3个写线程
        for (int i = 3; i < 6; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.put(finalI + "", finalI + ""); // 写
            }, String.valueOf(i)).start();
        }

        // 再 注册3个读线程
        for (int i = 3; i < 6; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.get(finalI + ""); // 读
            }, String.valueOf(i)).start();
        }

        // 再 注册3个读线程
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.get(finalI + ""); // 读
            }, String.valueOf(i)).start();
        }
    }
}
