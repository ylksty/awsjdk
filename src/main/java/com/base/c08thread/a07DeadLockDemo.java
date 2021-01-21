package com.base.c08thread;

import java.util.concurrent.TimeUnit;

/**
 * @author joe
 * @create 2021-01-21 18:44
 */
public class a07DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new DeadLockFoo(lockA, lockB), "A").start();
        new Thread(new DeadLockFoo(lockB, lockA), "B").start();
    }
    public static class DeadLockFoo implements Runnable {
        private final String lockA;
        private final String lockB;

        public DeadLockFoo(String lockA, String lockB) {
            this.lockA = lockA;
            this.lockB = lockB;
        }

        @Override
        public void run() {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + "\t获得锁:" + lockA + "\t尝试获得:" + lockB);
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (lockB) {
                    System.out.println(Thread.currentThread().getName() + "\t获得锁:" + lockB + "\t尝试获得:" + lockA);
                }
            }
        }
    }
}
