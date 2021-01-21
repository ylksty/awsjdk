package com.base.c08thread.a05blockingqueue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author joe
 * @create 2021-01-14 16:29
 */
/*
生产者消费者模式_传统版
lock: lock/await/signalAll 代替 synchronized：sync/wait/notify
 */
public class ProdConsumer_Traditional {
    public static class ShareData{
        private AtomicInteger atomicInteger = new AtomicInteger();
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();
        public void increment() {
            lock.lock();
            try {
                // 1.检查
                while (atomicInteger.get() != 0) {
                    // 等待生成
                    condition.await();
                }
                // 2.生成
                int i = atomicInteger.incrementAndGet();
                System.out.println(Thread.currentThread().getName() + "\t材料：" + i);
//                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                // 3.通知唤醒
                condition.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        public void decrement(Integer num) {
            lock.lock();
            try {
                // 1.检查
                while (atomicInteger.get() == 0) { // 这里必须用while，阻塞在condition.await();通知以后还能回上来
                    System.out.println(Thread.currentThread().getName() + "\t等待。。。");
                    // 等待消费
                    condition.await();
                }
                // 2.消费
                int i = atomicInteger.decrementAndGet();
                System.out.println(Thread.currentThread().getName() + "\t材料：" + i);
                System.out.println(Thread.currentThread().getName() + "\t第" + num + "次消费，并通知唤醒");
                // 3.通知唤醒
                condition.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                shareData.decrement(i);
            }
        }, "消费者B").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                shareData.decrement(i);
            }
        }, "消费者C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.increment();
            }
        }, "生产者A").start();

    }
}
