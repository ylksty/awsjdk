package com.base.c08thread.a04lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author joe
 * @create 2021-01-21 10:46
 */
/*
 4.3.自旋锁
 */
public class SpinLockDemo {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();
    public void lock() {
        Thread thread = Thread.currentThread();
        int c = 0;
        int t = 100000000;
        while (!atomicReference.compareAndSet(null, thread)) {
            c++;
            if (c%t == 0) {
                System.out.println(Thread.currentThread().getName() + "\t循环等待：" + c);
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t get lock");
    }
    public void unlock() {
        Thread thread = Thread.currentThread();
        boolean b = atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t释放锁：" + b);
    }
    public static void main(String[] args) {
        SpinLockDemo lockDemo = new SpinLockDemo();
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                lockDemo.lock();
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                lockDemo.unlock();
            }, String.valueOf(i)).start();
        }
    }
}
